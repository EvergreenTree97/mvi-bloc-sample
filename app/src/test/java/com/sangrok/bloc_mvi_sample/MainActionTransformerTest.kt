package com.sangrok.bloc_mvi_sample

import com.sangrok.bloc_mvi_sample.repository.MockRepositoryImpl
import com.sangrok.bloc_mvi_sample.ui.main.MainAction
import com.sangrok.bloc_mvi_sample.ui.main.MainActionTransformer
import com.sangrok.bloc_mvi_sample.ui.main.Member
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MainActionTransformerTest {
    val repository: MockRepositoryImpl = mockk()
    val mainActionTransformer: MainActionTransformer = spyk(MainActionTransformer(repository))

    @Test
    fun `clickToggle시에 false에서 true로 바뀐다`() = runTest {
        val member = Member(name = "상록", liked = false)
        val action = MainAction.ClickToggle(member = member)

        val expected = MainAction.SetMemberState(member = member.copy(liked = member.liked.not()))
        coEvery { repository.like(member) } returns member.copy(liked = member.liked.not())

        val actual = mainActionTransformer.transformActions(action).first()

        assertEquals(expected, actual)
    }

    @Test
    fun `clickToggle시에 true에서 false로 바뀐다`() = runTest {
        val member = Member(name = "상록", liked = true)
        val action = MainAction.ClickToggle(member = member)

        val expected = MainAction.SetMemberState(member = member.copy(liked = member.liked.not()))
        coEvery { repository.like(member) } returns member.copy(liked = member.liked.not())

        val actual = mainActionTransformer.transformActions(action).first()

        assertEquals(expected, actual)
    }

    @Test
    fun `repository 통신이 실패했을 때 롤백 해주는지`() = runTest {
        val member = Member(name = "상록", liked = true)
        val action = MainAction.ClickToggle(member = member)

        val expected = MainAction.SetMemberState(member = member.copy(liked = member.liked))
        coEvery { repository.like(member) }.throws(IllegalStateException())

        val actual = mainActionTransformer.transformActions(action).first()

        assertEquals(expected, actual)
    }
}