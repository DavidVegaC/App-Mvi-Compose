package com.davega.ui.compose.stateful.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.davega.ui.core.viewmodel.handler.UiEffect
import kotlin.reflect.KClass

class UiEffectStore {

    val modalUiEffectStore = mutableStateMapOf<KClass<*>, @Composable () -> Unit>()

    fun <E: UiEffect> bindComposableWithUiEffect(
        kClass: KClass<E>,
        effect: @Composable () -> Unit
    ) {
        modalUiEffectStore[kClass] = effect
    }
}

val LocalUiEffectStore = staticCompositionLocalOf<UiEffectStore> {
    error("No UiEffectStore provided")
}

@Composable
fun UiEffectStoreProvider(
    content: @Composable () -> Unit
) {
    val uiEffectStore = remember { UiEffectStore() }
    CompositionLocalProvider(
        LocalUiEffectStore provides uiEffectStore
    ) {
        content()
    }
}