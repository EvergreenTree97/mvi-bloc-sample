package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ActionTransformer
import com.sangrok.bloc_mvi_sample.repository.MockRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class MainActionTransformer(
    private val memberRepository: MockRepositoryImpl,
) : ActionTransformer<MainAction> {
    override suspend fun transformActions(action: MainAction): Flow<MainAction> {
        return when (action) {
            is MainAction.ClickToggle -> clickToggle(action)
            else -> flowOf(action)
        }
    }

    private fun clickToggle(action: MainAction.ClickToggle): Flow<MainAction> = flow {
        val cache = action.member
        val member = memberRepository.like(cache)
        emit(
            MainAction.SetMemberState(member = member)
        )
    }

//    private fun clickToggle(action: MainAction.ClickToggle): Flow<MainAction> = flow {
//        val cache = action.member
//        emit(MainAction.SetMemberState(action.member.copy(liked = action.member.liked.not())))
//        runCatching {
//            memberRepository.like(action.member)
//        }.onFailure {
//            emit(MainAction.SetMemberState(cache))
//        }
//    }
}