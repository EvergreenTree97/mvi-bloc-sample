package com.sangrok.bloc_mvi_sample

import com.sangrok.bloc_mvi_sample.bloc.ActionTransformer
import com.sangrok.bloc_mvi_sample.repository.MockRepository
import com.sangrok.bloc_mvi_sample.ui.main.MainAction
import com.sangrok.bloc_mvi_sample.ui.main.MainActionTransformer
import com.sangrok.bloc_mvi_sample.ui.main.Member
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class MainActionTransformerTest {
    @Mock
    lateinit var mockRepository: MockRepository
    lateinit var mainActionTransformer: ActionTransformer<MainAction>

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        mainActionTransformer = MainActionTransformer(mockRepository)
    }

    @Test
    fun `GIVEN 좋아요 상태 false 멤버 WHEN 토클 클릭 THEN 좋아요 상태가 true로 변경`() = runTest {
        // GIVEN
        val member = Member("정민지", false)
        val action = MainAction.ClickToggle(member)

        // WHEN
        val actual = mainActionTransformer.transformActions(action).last()

        // THEN
        val expect = MainAction.SetMemberState(member.copy(liked = true))
        Assert.assertEquals(expect, actual)
    }

    @Test
    fun `GIVEN 좋아요 상태 true 멤버 WHEN 토클 클릭 THEN 좋아요 상태가 false로 변경`() = runTest {
        // GIVEN
        val member = Member("정민지", true)
        val action = MainAction.ClickToggle(member)

        // WHEN
        val actual = mainActionTransformer.transformActions(action).last()

        // THEN
        val expect = MainAction.SetMemberState(member.copy(liked = false))
        Assert.assertEquals(expect, actual)
    }

    @Test
    fun `GIVEN 멤버 WHEN 토클 클릭 후 서버 api 호출 실패 THEN 멤버 상태 롤백`() = runTest {
        // GIVEN
        val member = Member("정민지", true)
        val action = MainAction.ClickToggle(member)

        whenever(mockRepository.like(member)).thenThrow()

        // WHEN
        val actual = mainActionTransformer.transformActions(action).last()

        // THEN
        val expect = MainAction.SetMemberState(member)
        Assert.assertEquals(expect, actual)
    }
}
