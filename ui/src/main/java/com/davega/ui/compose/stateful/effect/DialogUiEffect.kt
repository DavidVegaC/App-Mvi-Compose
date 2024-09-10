package com.davega.ui.compose.stateful.effect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.davega.ui.compose.stateful.Stateful
import com.davega.ui.compose.stateful.core.disableUiEffect
import com.davega.ui.compose.stateful.findViewModel
import com.davega.ui.core.viewmodel.handler.UiEffect
import kotlin.reflect.KClass

interface UiEffectDialogScope {
    fun dismiss()

    companion object {

        fun create(onDismissRequest: () -> Unit): UiEffectDialogScope {
            return DefaultUiEffectDialogScope(onDismissRequest)
        }
    }
}

private class DefaultUiEffectDialogScope(
    private val onDismissRequest: () -> Unit
): UiEffectDialogScope {

    override fun dismiss() {
        onDismissRequest()
    }
}

@Composable
fun DialogContainerPreview(
    isFullScreen: Boolean,
    content: @Composable () -> Unit
) {
    BoxWithConstraints {
        val minWith = maxWidth * 0.844f
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(alpha = 0.3f)
                ),
            contentAlignment = if (isFullScreen) {
                Alignment.TopStart
            } else {
                Alignment.Center
            }
        ) {
            if (isFullScreen) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    content()
                }
            } else {
                Box(
                    modifier = Modifier
                        .widthIn(
                            min = minWith,
                            max = 304.dp
                        )
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun <E: UiEffect> DialogUiEffect(
    scope: Stateful<*>,
    kClass: KClass<E>,
    isCancelable: Boolean = true,
    isFullScreen: Boolean = false,
    content: @Composable UiEffectDialogScope.(E) -> Unit
) {
    ModalUiEffect(
        scope = scope,
        kClass = kClass,
        preview = { uiEffect ->
            DialogContainerPreview(
                isFullScreen = isFullScreen
            ) {
                val modalScope = remember {
                    UiEffectDialogScope.create {}
                }
                modalScope.content(uiEffect)
            }
        }
    ) { uiEffect ->
        val modalScope = remember {
            UiEffectDialogScope.create {
                disableUiEffect(
                    handler = scope.findViewModel(),
                    kClass = kClass
                )
            }
        }
        Dialog(
            onDismissRequest = {
                modalScope.dismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = isCancelable,
                dismissOnClickOutside = isCancelable,
                usePlatformDefaultWidth = !isFullScreen
            ),
        ) {
            if (isFullScreen) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    content.invoke(modalScope, uiEffect)
                }
            } else {
                content.invoke(modalScope, uiEffect)
            }
        }
    }
}

@Composable
inline fun <reified E: UiEffect> Stateful<*>.DialogUiEffect(
    isCancelable: Boolean = true,
    isFullScreen: Boolean = false,
    noinline content: @Composable UiEffectDialogScope.(E) -> Unit
) {
    DialogUiEffect(
        scope = this,
        kClass = E::class,
        isCancelable = isCancelable,
        isFullScreen = isFullScreen,
        content = content
    )
}