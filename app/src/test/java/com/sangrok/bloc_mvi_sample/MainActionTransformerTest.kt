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




}