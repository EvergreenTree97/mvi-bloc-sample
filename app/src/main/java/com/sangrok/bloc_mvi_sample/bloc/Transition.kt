package com.sangrok.bloc_mvi_sample.bloc

data class Transition<STATE : Any, ACTION : Any>(
    val currentState: STATE,
    val action: ACTION,
    val nextState: STATE,
)