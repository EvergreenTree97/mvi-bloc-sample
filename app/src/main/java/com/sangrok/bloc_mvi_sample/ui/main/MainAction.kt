package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ViewAction

sealed class MainAction : ViewAction {
    object ClickButton : MainAction()

    data class ClickToggle(val member: Member): MainAction()

    data class SetMemberState(val member: Member): MainAction()
    data class ClickTab(val selectedTab: Tab) : MainAction()

    object DialogDismiss: MainAction()
}
