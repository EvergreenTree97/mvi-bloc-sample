package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ActionTransformer
import com.sangrok.bloc_mvi_sample.repository.MockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class MainActionTransformer(
    private val memberRepository: MockRepository,
) : ActionTransformer<MainAction> {
    override suspend fun transformActions(action: MainAction): Flow<MainAction> {
        return when (action) {
            is MainAction.ClickToggle -> clickToggle(action)
            else -> flowOf(action)
        }
    }

    private fun clickToggle(action: MainAction.ClickToggle): Flow<MainAction> = flow {
        val cache = action.member
        emit(MainAction.SetMemberState(cache.copy(liked = cache.liked.not())))
        runCatching {
            memberRepository.like(cache)
        }.onFailure {
            emit(MainAction.SetMemberState(cache))
        }
    }
}