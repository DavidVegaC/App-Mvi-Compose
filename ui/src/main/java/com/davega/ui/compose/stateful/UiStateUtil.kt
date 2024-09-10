package com.davega.ui.compose.stateful

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.lifecycle.ViewModel
import com.davega.ui.compose.util.uiStatePreview
import com.davega.ui.core.viewmodel.handler.UiState
import com.davega.ui.core.viewmodel.handler.UiStateHandler

@Composable
inline fun <reified S: UiState, V> Stateful<V>.uiState(): State<S>
where  V: ViewModel, V: UiStateHandler<S> {
    return if (LocalInspectionMode.current) {
        uiStatePreview(S::class)
    } else {
        val handler = findViewModel()
        handler.uiStateFlow.collectAsState(initial = handler.uiState)
    }
}

fun <S: UiState, V> Stateful<V>.setUiState(reduce: S.() -> S)
where  V: ViewModel, V: UiStateHandler<S> {
    val viewModel = findViewModel()
    return viewModel.setUiState(reduce(viewModel.uiState))
}