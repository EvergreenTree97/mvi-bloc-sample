package com.sangrok.bloc_mvi_sample.bloc

import kotlinx.coroutines.flow.Flow

interface ErrorMapper<STATE, ACTION> {
    suspend fun mapErrorToAction(state: STATE, action: ACTION, throwable: Throwable): Flow<ACTION>
    suspend fun mapErrorToState(state: STATE, action: ACTION, throwable: Throwable): Flow<STATE>
}