@file:OptIn(ExperimentalCoroutinesApi::class)

package com.sangrok.bloc_mvi_sample.bloc

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class Bloc<STATE : Any, ACTION : Any>(
    initialState: STATE,
    private val actionMapper: ActionMapper<STATE, ACTION>,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) {
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    val currentState: STATE
        get() = stateFlow.value

    private val actionFlow = Channel<ACTION>()
    private var job: Job? = null

    fun start() {
        job = actionFlow.receiveAsFlow()
            .flatMapConcat { action -> mapActionToState(action) }
            .filter { it.currentState != it.nextState }
            .onEach {
                doOnTransition(it)
            }
            .launchIn(scope)
    }

    private suspend fun mapActionToState(action: ACTION): Flow<Transition<STATE, ACTION>> {
        return actionMapper.mapActionToState(action, currentState).map {
            Transition(currentState, action, it)
        }
    }

    fun dispatch(action: ACTION) {
        scope.launch {
            actionFlow.send(action)
        }
    }

    private suspend fun doOnTransition(transition: Transition<STATE, ACTION>) {
        _stateFlow.emit(transition.nextState)
    }

    fun end() {
        scope.cancel()
        actionFlow.close()
    }
}