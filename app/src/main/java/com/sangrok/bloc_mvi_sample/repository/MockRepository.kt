package com.sangrok.bloc_mvi_sample.repository

import com.sangrok.bloc_mvi_sample.ui.main.Member
import kotlinx.coroutines.delay

class MockRepository {
    suspend fun getMembers(): List<Member> {
        delay(1000L)
        return listOf(
            Member("상록", false),
            Member("지혜", false),
            Member("재성", false),
            Member("다연", false),
            Member("석주", false),
            Member("민지", false),
        )
    }

    suspend fun toggleLike(member: Member): Member {
        delay(4000L)
        error("메롱")
        return member.copy(liked = member.liked.not())
    }
}