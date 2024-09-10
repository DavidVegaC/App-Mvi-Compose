package com.davega.ui.core.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davega.ui.core.viewmodel.handler.LoaderHandler
import com.davega.ui.core.viewmodel.handler.UiEffect
import com.davega.ui.core.viewmodel.handler.UiEffectHandler
import com.davega.ui.core.viewmodel.handler.UiEvent
import com.davega.ui.core.viewmodel.handler.UiEventHandler
import com.davega.ui.core.viewmodel.handler.UiIntent
import com.davega.ui.core.viewmodel.handler.UiIntentHandler
import com.davega.ui.core.viewmodel.handler.UiState
import com.davega.ui.core.viewmodel.handler.UiStateHandler
import com.davega.ui.core.viewmodel.handler.loaderHandler
import com.davega.ui.core.viewmodel.handler.uiEffectHandler
import com.davega.ui.core.viewmodel.handler.uiEventHandler
import com.davega.ui.core.viewmodel.handler.uiIntentHandler
import com.davega.ui.core.viewmodel.handler.uiStateHandler

abstract class BaseViewModel<S : UiState, I : UiIntent, E : UiEvent>(
    savedStateHandle: SavedStateHandle,
    defaultUiState: SavedStateHandle.() -> S
) : ViewModel(),
    UiStateHandler<S> by uiStateHandler(savedStateHandle, defaultUiState),
    UiIntentHandler<I> by uiIntentHandler(),
    UiEventHandler<E> by uiEventHandler(),
    LoaderHandler by loaderHandler(),
    UiEffectHandler by uiEffectHandler()
{

    init {
        // Inicializa el ViewModel con el scope del ViewModel y la función handleIntent
        this.initialize(viewModelScope, ::handleIntent)
    }

    protected abstract suspend fun handleIntent(intent: I)

    inline fun <reified E: UiEffect> E.enable(timeMillis: Long? = null) {
        if (timeMillis == null) {
            enableUiEffect(E::class, this)
            return
        }
        enableUiEffect(E::class, this, timeMillis = timeMillis)
    }

    inline fun <reified E: UiEffect> E.disable() {
        disableUiEffect(E::class)
    }
}