package com.sangrok.bloc_mvi_sample.bloc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class DefaultErrorMapper<STATE, ACTION> : ErrorMapper<STATE, ACTION> {
    override suspend fun mapErrorToAction(
        state: STATE,
        action: ACTION,
        throwable: Throwable
    ): Flow<ACTION> {
        return emptyFlow()
    }

    override suspend fun mapErrorToState(
        state: STATE,
        action: ACTION,
        throwable: Throwable
    ): Flow<STATE> {
        return emptyFlow()
    }
}

class DefaultActionTransFormer<ACTION> : ActionTransformer<ACTION> {
    override suspend fun transformActions(action: ACTION): Flow<ACTION> {
        return flowOf(action)
    }

}