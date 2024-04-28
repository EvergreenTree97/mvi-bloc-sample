package com.sangrok.bloc_mvi_sample

import com.sangrok.bloc_mvi_sample.bloc.ActionMapper
import com.sangrok.bloc_mvi_sample.repository.MockRepository
import com.sangrok.bloc_mvi_sample.ui.main.MainAction
import com.sangrok.bloc_mvi_sample.ui.main.MainActionMapper
import com.sangrok.bloc_mvi_sample.ui.main.MainState
import com.sangrok.bloc_mvi_sample.ui.main.Member
import com.sangrok.bloc_mvi_sample.ui.main.Tab
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActionMapperTest {

    @Mock
    lateinit var mockRepository: MockRepository

    lateinit var mainActionMapper: ActionMapper<MainState, MainAction>

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        mainActionMapper = MainActionMapper(mockRepository)
    }

    @Test
    fun `GIVEN 멤버 목록 WHEN 버튼 클릭 시 THEN 멤버 정보를 가져온다`() = runTest {
        //GIVEN
        val givenState = MainState.INITIAL_STATE.copy(isLoading = true)
        val members = (0..5).map {
            Member(
                name = "$it",
                liked = false
            )
        }
        whenever(mockRepository.getMembers()).thenReturn(members)

        //WHEN
        val action = MainAction.ClickButton
        val actual = mainActionMapper.mapActionToState(action, givenState).last()

        //THEN
        val expect = givenState.copy(
            members = members,
            isLoading = false,
        )
        Assert.assertEquals(expect, actual)
    }

    @Test
    fun `GIVEN 멤버 목록, 선택된 탭 WHEN 탭 변경 THEN 선택한 탭으로 변경하고, 멤버목록 필터링`() = runTest {
        Tab.values().forEach { tab ->
            val givenState = MainState.INITIAL_STATE
            val members = (0..5).map {
                Member(
                    name = "$it",
                    liked = false
                )
            }
            whenever(mockRepository.getMembers()).thenReturn(members)
            val filteredMembers = when(tab){
                Tab.EVEN -> members.filterIndexed { index, member ->  index % 2 == 0 }
                Tab.ODD -> members.filterIndexed { index, member ->  index % 2 == 1 }
            }

            //WHEN
            val action = MainAction.ClickTab(tab)
            val actual = mainActionMapper.mapActionToState(action, givenState).last()

            //THEN
            val expect = givenState.copy(
                members = filteredMembers,
                currentTab = tab,
                isLoading = false,
            )
            Assert.assertEquals(expect, actual)
        }
    }

    @Test
    fun `GIVEN 변경해야하는 멤버 WHEN 멤버 상태 변경 THEN 멤버 리스트에서 특정 멤버 상태를 변경`() = runTest {
        // GIVEN
        val members = (0..5).map {
            Member(
                name = "$it",
                liked = false
            )
        }

        val givenState = MainState.INITIAL_STATE.copy(
            members = members
        )

        val changedMember = Member("0", true)
        val action = MainAction.SetMemberState(changedMember)

        // WHEN
        val actual = mainActionMapper.mapActionToState(action, givenState).last()

        // THEN
        val expect = givenState.copy(
            members = members.map { if (it.name == "0") changedMember else it }
        )
        Assert.assertEquals(expect, actual)
    }
}