package com.sangrok.bloc_mvi_sample.bloc

import kotlinx.coroutines.flow.Flow

interface ActionTransformer<ACTION> {
    suspend fun transformActions(action: ACTION): Flow<ACTION>
}