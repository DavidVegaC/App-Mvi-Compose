package com.davega.ui.core.viewmodel.handler

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface UiState

interface UiStateHandler<S: UiState> {

    val uiStateFlow: Flow<S>

    val uiState: S

    fun setUiState(uiState: S)
}

inline fun <S: UiState> UiStateHandler<S>.setUiState(reduce: S.() -> S) {
    setUiState(reduce(uiState))
}

private class DefaultUiStateHandler<S: UiState>(
    savedStateHandle: SavedStateHandle,
    defaultUiState: SavedStateHandle.() -> S
): UiStateHandler<S> {

    private val _state = MutableStateFlow(defaultUiState(savedStateHandle))
    override val uiStateFlow get() = _state
    override val uiState get() = _state.value

    @Synchronized
    override fun setUiState(uiState: S) {
        _state.tryEmit(uiState)
    }
}

fun <S: UiState> uiStateHandler(savedStateHandle: SavedStateHandle, defaultUiState: SavedStateHandle.() -> S): UiStateHandler<S> {
    return DefaultUiStateHandler(savedStateHandle, defaultUiState)
}