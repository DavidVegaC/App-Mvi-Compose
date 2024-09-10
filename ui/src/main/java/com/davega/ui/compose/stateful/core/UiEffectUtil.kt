package com.davega.ui.compose.stateful.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import com.davega.ui.compose.stateful.Stateful
import com.davega.ui.compose.stateful.findViewModel
import com.davega.ui.compose.util.uiEffectPreview
import com.davega.ui.core.viewmodel.handler.UiEffect
import com.davega.ui.core.viewmodel.handler.UiEffectHandler
import kotlin.reflect.KClass

private val UiEffectNotImplemented = IllegalStateException("ViewModel must implement UiEffectHandler")

fun <E: UiEffect> disableUiEffect(
    handler: Any,
    kClass: KClass<E>
) {
    if (handler is UiEffectHandler) {
        handler.disableUiEffect(kClass)
    } else {
        throw UiEffectNotImplemented
    }
}

fun <E: UiEffect> enableUiEffect(
    handler: Any,
    kClass: KClass<E>,
    value: E,
    timeMillis: Long? = null
) {
    if (handler is UiEffectHandler) {
        if (timeMillis == null) {
            handler.enableUiEffect(kClass, value)
        } else {
            handler.enableUiEffect(kClass, value, timeMillis)
        }
    } else {
        throw UiEffectNotImplemented
    }
}

fun disableAllUiEffects(
    handler: Any
) {
    if (handler is UiEffectHandler) {
        handler.deactivateAllUiEffects()
    } else {
        throw UiEffectNotImplemented
    }
}

@Composable
fun <E: UiEffect> Stateful<*>.uiEffect(
    kClass: KClass<E>,
): State<E?> {
    if (LocalInspectionMode.current) {
        val provider = uiEffectPreview()
        return remember {
            val effect = provider.uiEffect(kClass = kClass)
            mutableStateOf(effect)
        }
    }

    val handler = findViewModel()
    return if (handler is UiEffectHandler) {
        handler.registerUiEffect(kClass).collectAsState(initial = handler.getUiEffect(kClass))
    } else {
        throw UiEffectNotImplemented
    }
}