package com.davega.ui.core.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.davega.ui.core.viewmodel.handler.UiEffect
import com.davega.ui.core.viewmodel.handler.UiEffectHandler
import com.davega.ui.core.viewmodel.handler.registerUiEffect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Extensión para Fragment que escucha efectos de UI.
 *
 * @param E El tipo del efecto de UI.
 * @param handler El UiEffectHandler responsable de gestionar los efectos de UI.
 * @param block Una función lambda que se ejecuta cuando se recibe un efecto de UI.
 */
inline fun <reified E: UiEffect> Fragment.onUiEffect(
    handler: UiEffectHandler,
    noinline block: (UiEffect?) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        handler.registerUiEffect<E>().collectLatest {
            block(it)
        }
    }
}

/**
 * Habilita un efecto de UI con una duración opcional.
 *
 * @param E El tipo del efecto de UI.
 * @param handler El UiEffectHandler responsable de gestionar los efectos de UI.
 * @param uiEffect El efecto de UI a habilitar.
 * @param timeMillis La duración en milisegundos durante la cual el efecto de UI debe estar habilitado. Si es null, el efecto se habilita indefinidamente.
 */
inline fun <reified E: UiEffect> enableUiEffect(
    handler: UiEffectHandler,
    uiEffect: E,
    timeMillis: Long? = null
) {
    if (timeMillis != null) {
        handler.enableUiEffect(E::class, uiEffect, timeMillis)
    } else {
        handler.enableUiEffect(E::class, uiEffect)
    }
}

/**
 * Deshabilita un efecto de UI.
 *
 * @param E El tipo del efecto de UI.
 * @param handler El UiEffectHandler responsable de gestionar los efectos de UI.
 */
inline fun <reified E: UiEffect> disableUiEffect(
    handler: UiEffectHandler
) {
    handler.disableUiEffect(E::class)
}