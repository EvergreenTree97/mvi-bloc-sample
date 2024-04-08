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

    suspend fun like(member: Member): Member {
        delay(100L)
        return member.copy(liked = member.liked.not())
    }

    suspend fun unLike(member: Member): Member {
        delay(100L)
        return member.copy(liked = member.liked.not())
    }
}