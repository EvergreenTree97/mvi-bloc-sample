package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.Bloc
import com.sangrok.bloc_mvi_sample.bloc.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    bloc: Bloc<MainState, MainAction>
) : BlocViewModel<MainState, MainAction>(bloc) {

}
