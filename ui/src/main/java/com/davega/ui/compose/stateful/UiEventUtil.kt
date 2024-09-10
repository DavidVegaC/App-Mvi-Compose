package com.davega.ui.compose.stateful

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.lifecycle.ViewModel
import com.davega.ui.core.viewmodel.handler.UiEventHandler
import kotlinx.coroutines.launch

@Composable
fun <V, E> Stateful<V>.OnUiEvent(
    handle: (uiEvent: E) -> Unit
) where V: ViewModel, V: UiEventHandler<E> {
    if (!LocalInspectionMode.current) {
        val scope = rememberCoroutineScope()
        remember {
            scope.launch {
                findViewModel().setOnUiEvent(handle)
            }
        }
    }
}