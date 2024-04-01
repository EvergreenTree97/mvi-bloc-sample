package com.sangrok.bloc_mvi_sample.bloc

import androidx.lifecycle.ViewModel

open class BlocViewModel<STATE : ViewState, ACTION : ViewAction>(
    private val bloc: Bloc<STATE, ACTION>
) : ViewModel() {

    val stateFlow = bloc.stateFlow

    val currentState: STATE
        get() = bloc.currentState

    init {
        bloc.start()
    }

    fun dispatch(action: ACTION) {
        bloc.dispatch(action)
    }

    override fun onCleared() {
        bloc.end()
        super.onCleared()
    }
}