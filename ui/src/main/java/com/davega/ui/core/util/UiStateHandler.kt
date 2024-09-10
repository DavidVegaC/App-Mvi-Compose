package com.davega.ui.core.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.davega.ui.core.viewmodel.handler.UiState
import com.davega.ui.core.viewmodel.handler.UiStateHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Establece el estado de la UI utilizando el manejador y el bloque proporcionados.
 *
 * @param S El tipo del estado de la UI.
 * @param handler El UiStateHandler responsable de gestionar el estado de la UI.
 * @param block Una función lambda que toma el estado actual de la UI y devuelve el nuevo estado de la UI.
 */
inline fun <S: UiState> setUiState(
    handler: UiStateHandler<S>,
    block: (S) -> S
) {
    handler.setUiState(block(handler.uiState))
}

/**
 * Extensión para Fragment que escucha cambios en el estado de la UI.
 *
 * @param S El tipo del estado de la UI.
 * @param handler El UiStateHandler responsable de gestionar el estado de la UI.
 * @param block Una función lambda que se ejecuta cuando se recibe un nuevo estado de la UI.
 */
fun <S: UiState> Fragment.onUiState(
    handler: UiStateHandler<S>,
    block: (S) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        handler.uiStateFlow.collectLatest {
            block(it)
        }
    }
}