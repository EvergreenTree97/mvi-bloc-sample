package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.Bloc
import com.sangrok.bloc_mvi_sample.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMemberRepository(): MemberRepository {
        return MemberRepository()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class BlocModule {

    @Provides
    fun provideMainBloc(
        memberRepository: MemberRepository
    ): Bloc<MainState, MainAction> {
        return Bloc(
            initialState = MainState.INITIAL_STATE,
            actionMapper = MainActionMapper(memberRepository)
        )
    }

}