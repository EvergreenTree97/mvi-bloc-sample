package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ActionMapper
import com.sangrok.bloc_mvi_sample.repository.MockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class MainActionMapper(
    private val memberRepository: MockRepository,
) : ActionMapper<MainState, MainAction> {
    override suspend fun mapActionToState(action: MainAction, state: MainState): Flow<MainState> {
        return when (action) {
            MainAction.ClickButton -> clickButton(state, action)
            is MainAction.ClickTab -> clickTab(state, action)
        }
    }

    private fun clickButton(state: MainState, action: MainAction): Flow<MainState> =
        flow {
            val members = memberRepository.getMembers()
            emit(
                state.copy(
                    isLoading = false,
                    members = members
                )
            )
        }.onStart {
            emit(state.copy(isLoading = true))
        }.catch {
            emit(state.copy(isError = true))
        }

    private fun clickTab(state: MainState, action: MainAction.ClickTab): Flow<MainState> = flow {
        val members = memberRepository.getMembers()
        val filteredMembers = when (action.selectedTab) {
            Tab.EVEN -> members.filterIndexed { index, _ -> index % 2 == 0 }
            Tab.ODD -> members.filterIndexed { index, _ -> index % 2 == 1 }
        }
        emit(
            state.copy(
                currentTab = action.selectedTab,
                members = filteredMembers
            )
        )
    }
}
