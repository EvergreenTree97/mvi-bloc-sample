package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ActionMapper
import com.sangrok.bloc_mvi_sample.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class MainActionMapper(
    private val memberRepository: MemberRepository,
) : ActionMapper<MainState, MainAction> {
    override suspend fun mapActionToState(action: MainAction, state: MainState): Flow<MainState> {
        return when (action) {
            MainAction.ClickButton -> clickButton(state, action)
        }
    }

    private fun clickButton(state: MainState, action: MainAction): Flow<MainState> =
        flow<MainState> {
            val members = memberRepository.getMembers()
            emit(
                state.copy(
                    isLoading = false,
                    members = members
                )
            )
        }.onStart {
            emit(state.copy(isLoading = true))
        }

}