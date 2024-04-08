package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ViewState

data class MainState(
    val isLoading: Boolean = false,
    val members: List<String> = listOf(),
    val isError: Boolean = false,
    val currentTab: Tab = Tab.Tab1,
) : ViewState {
    companion object {
        val INITIAL_STATE = MainState()
    }
}

enum class Tab {
    Tab1,
    Tab2,
}