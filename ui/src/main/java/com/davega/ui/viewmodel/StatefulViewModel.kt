package com.davega.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class StatefulViewModel<UiState, UiEvent>(
    state: UiState,
    event: UiEvent? = null
): StatelessViewModel<UiEvent>(event = event) {

    protected var state: UiState by mutableStateOf(state)

    val uiState get() = state

    protected inline fun setUiState(block: UiState.() -> UiState){
        state = uiState.block()
    }

}