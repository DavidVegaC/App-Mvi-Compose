package com.davega.ui.compose.stateful

import com.davega.ui.compose.stateful.core.disableUiEffect
import com.davega.ui.core.viewmodel.handler.UiEffect

inline fun <reified E: UiEffect> Stateful<*>.disableUiEffect() {
    disableUiEffect(
        handler = findViewModel(),
        kClass = E::class
    )
}

inline fun <reified E: UiEffect> Stateful<*>.enableUiEffect(
    value: E,
    timeMillis: Long? = null
) {

}

fun Stateful<*>.disableAllUiEffects() {

}