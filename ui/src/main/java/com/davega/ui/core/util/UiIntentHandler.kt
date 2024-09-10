package com.davega.ui.core.util

import com.davega.ui.core.viewmodel.handler.UiIntent
import com.davega.ui.core.viewmodel.handler.UiIntentHandler

/**
 * Ejecuta un intento de UI utilizando el manejador proporcionado.
 *
 * @param I El tipo del intento de UI.
 * @param handler El UiIntentHandler responsable de gestionar los intentos de UI.
 * @param intent El intento de UI a ejecutar.
 */
fun <I: UiIntent> execUiIntent(
    handler: UiIntentHandler<I>,
    intent: I
) {
    handler.execUiIntent(intent)
}