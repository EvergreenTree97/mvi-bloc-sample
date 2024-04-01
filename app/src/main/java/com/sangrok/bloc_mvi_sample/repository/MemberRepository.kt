package com.sangrok.bloc_mvi_sample.repository

import kotlinx.coroutines.delay

class MemberRepository {
    suspend fun getMembers(): List<String> {
        delay(1000L)
        return listOf(
            "상록",
            "지혜",
            "재성",
            "다연",
            "석주",
            "민지,"
        )
    }
}