package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ViewAction

sealed class MainAction : ViewAction {
    object ClickButton : MainAction()

    data class ClickTab(val selectedTab: Tab) : MainAction()

}
