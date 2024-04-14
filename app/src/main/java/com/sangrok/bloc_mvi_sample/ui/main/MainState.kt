package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ViewState

data class MainState(
    val isLoading: Boolean = false,
    val members: List<Member> = listOf(),
    val currentTab: Tab = Tab.ODD,
    val errorDialogVisible: Boolean = false,
) : ViewState {
    companion object {
        val INITIAL_STATE = MainState()
    }
}

data class Member(
    val name: String,
    val liked: Boolean,
)

enum class Tab {
    ODD,
    EVEN,
}