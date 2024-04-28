package com.sangrok.bloc_mvi_sample.repository

import com.sangrok.bloc_mvi_sample.ui.main.Member
import kotlinx.coroutines.delay
import javax.inject.Inject

interface MockRepository{
    suspend fun getMembers(): List<Member>
    suspend fun like(member: Member): Member
}

class MockRepositoryImpl @Inject constructor(

): MockRepository {
    override suspend fun getMembers(): List<Member> {
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

    override suspend fun like(member: Member): Member {
        delay(3000L)
        return member.copy(liked = member.liked.not())
    }
}