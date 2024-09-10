package com.davega.ui.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.davega.ui.core.viewmodel.handler.UiEffect
import com.davega.ui.core.viewmodel.handler.UiState
import com.davega.ui.core.viewmodel.handler.UiStateHandler
import kotlin.reflect.KClass

/**
 * Excepción utilizada para indicar que se está utilizando el [LocalUiStatePreview] sin proporcionar un estado de vista de interfaz de usuario (UI State).
 */
val LocalUiStateException = ExceptionInInitializerError("use PreviewUiState")

/**
 * LocalComposition para proporcionar un estado de vista de interfaz de usuario (UI State) de vista previa.
 */
val LocalUiStatePreview: ProvidableCompositionLocal<Any> = compositionLocalOf {
    throw LocalUiStateException
}

val LocalUiEffectPreview: ProvidableCompositionLocal<UiEffectPreviewProvider>  = compositionLocalOf {
    UiEffectPreviewProvider()
}

class UiEffectPreviewProvider(
    vararg uiEffects: Pair<KClass<*>, Any?>
) {
    private val uiEffects = uiEffects.toMap()

    @Suppress("UNCHECKED_CAST")
    fun <E: UiEffect> uiEffect(kClass: KClass<E>): E? {
        return uiEffects[kClass] as E?
    }
}

fun uiEffectProviderOf(vararg uiEffects: Pair<KClass<*>, Any?>): UiEffectPreviewProvider {
    return UiEffectPreviewProvider(*uiEffects)
}

inline fun <reified E: UiEffect> E.provide(): Pair<KClass<E>, E> {
    return E::class to this
}

@Composable
fun uiEffectPreview(): UiEffectPreviewProvider {
    return LocalUiEffectPreview.current
}

@Composable
fun <S: UiState, V> ScreenPreview(
    uiState: S,
    uiEffectProvider: UiEffectPreviewProvider = UiEffectPreviewProvider(),
    content: @Composable () -> Unit
) where  V: ViewModel, V: UiStateHandler<S> {
    CompositionLocalProvider(
        LocalUiStatePreview provides uiState,
        LocalUiEffectPreview provides uiEffectProvider
    ) {
        content()
    }
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <S: UiState> uiStatePreview(kClass: KClass<S>): State<S> {
    val uiState = LocalUiStatePreview.current
    return if (uiState::class == kClass) {
        remember { mutableStateOf(uiState as S) }
    } else {
        throw LocalUiStateException
    }
}

/**
 * Composable que permite obtener un estado de vista de interfaz de usuario (UI State) de vista previa de tipo [S].
 *
 * @return Un [State] que representa el estado de vista de interfaz de usuario de vista previa.
 */
@Composable
inline fun <reified S: UiState> uiStatePreview(): State<S> {
    return uiStatePreview(kClass = S::class)
}