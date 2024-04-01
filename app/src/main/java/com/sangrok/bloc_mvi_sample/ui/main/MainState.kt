package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ViewState

data class MainState(
    val isLoading: Boolean = false,
    val members: List<String> = listOf(),
) : ViewState {
    companion object {
        val INITIAL_STATE = MainState()
    }
}