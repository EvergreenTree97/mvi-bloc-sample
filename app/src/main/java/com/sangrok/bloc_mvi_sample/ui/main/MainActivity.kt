package com.sangrok.bloc_mvi_sample.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.sangrok.bloc_mvi_sample.ui.theme.BlocmvisampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocmvisampleTheme {
                val state by viewModel.stateFlow.collectAsState()
                MainScreen(state = state, onAction = viewModel::dispatch)
            }
        }
    }
}

@Composable
fun MainScreen(
    state: MainState,
    onAction: (MainAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isError -> {
                ErrorScreen()
            }

            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.members.isEmpty() -> {
                Button(
                    onClick = {
                        onAction(MainAction.ClickButton)
                    }) {

                    Text("멤버 불러오기")
                }
            }

            else -> {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Tab.values().forEach {
                            Button(
                                onClick = {
                                    onAction(MainAction.ClickTab(it))
                                }) {
                                Text(it.name)
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(state.members) {
                            Row {
                                Text(it.name, fontSize = 24.sp)
                                //Toggle
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Toggle(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
) {
    Button(
        onClick = { onSelectedChange(selected.not()) }
    ) {
        if (selected) {
            Text("좋아요", color = Color.Red)
        } else {
            Text("싫어요")
        }
    }
}

@Composable
fun ErrorScreen() {
    Text(text = "Error")
}
