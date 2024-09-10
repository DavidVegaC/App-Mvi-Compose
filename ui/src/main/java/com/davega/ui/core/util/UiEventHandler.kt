package com.davega.ui.core.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.davega.ui.core.viewmodel.handler.UiEvent
import com.davega.ui.core.viewmodel.handler.UiEventHandler
import kotlinx.coroutines.launch

/**
 * Función de extensión para AppCompatActivity que permite registrar un oyente para eventos de interfaz de usuario (UI).
 *
 * @param E El tipo de evento de interfaz de usuario que se espera recibir.
 * @param handler Un ViewModel que implementa la interfaz [UiEventHandler<E>] y proporciona eventos de interfaz de usuario.
 * @param block El bloque suspendido que será llamado cuando se produzca un evento de interfaz de usuario.
 */
fun <E: UiEvent> AppCompatActivity.onUiEvent(
    handler: UiEventHandler<E>,
    block: suspend (E) -> Unit
) {
    // Utiliza el ciclo de vida de la actividad para registrar el oyente de eventos de interfaz de usuario.
    lifecycleScope.launch {
        handler.setOnUiEvent(block)
    }
}

/**
 * Función de extensión para Fragment que permite registrar un oyente para eventos de interfaz de usuario (UI).
 *
 * @param E El tipo de evento de interfaz de usuario que se espera recibir.
 * @param handler Un objeto que implementa la interfaz [UiEventHandler<E>] y proporciona eventos de interfaz de usuario.
 * @param block El bloque suspendido que será llamado cuando se produzca un evento de interfaz de usuario.
 */
fun <E: UiEvent> Fragment.onUiEvent(
    handler: UiEventHandler<E>,
    block: suspend (E) -> Unit
){
    // Utiliza el ciclo de vida de la vista del Fragment para registrar el oyente de eventos de interfaz de usuario.
    viewLifecycleOwner.lifecycleScope.launch {
        handler.setOnUiEvent(block)
    }
}