package com.sangrok.bloc_mvi_sample.bloc

import com.sangrok.bloc_mvi_sample.repository.MockRepository
import com.sangrok.bloc_mvi_sample.ui.main.MainAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MainActionTransFormer @Inject constructor(
    private val memberRepository: MockRepository,
) : ActionTransformer<MainAction> {
    override suspend fun transformActions(action: MainAction): Flow<MainAction> {
        return when (action) {
            MainAction.ClickButton,
            is MainAction.SetMemberState,
            is MainAction.ClickTab -> flowOf(action)

            is MainAction.ClickLikeButton -> {
                toggleAction(action)
            }
        }
    }

    private suspend fun toggleAction(action: MainAction.ClickLikeButton): Flow<MainAction.SetMemberState> =
        flow {
            emit(MainAction.SetMemberState(action.member.copy(liked = action.member.liked.not())))

            kotlin.runCatching {
                memberRepository.toggleLike(member = action.member)
            }.onFailure {
                emit(MainAction.SetMemberState(action.member.copy(liked = action.member.liked)))
            }
        }

}