package com.davega.ui.compose.stateful.effect

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import com.davega.ui.compose.stateful.Stateful
import com.davega.ui.compose.stateful.core.uiEffect
import com.davega.ui.compose.util.OnInit
import com.davega.ui.core.viewmodel.handler.UiEffect
import com.davega.ui.core.viewmodel.handler.ifEnabled
import kotlin.reflect.KClass

@Composable
fun <E: UiEffect> ModalUiEffect(
    scope: Stateful<*>,
    kClass: KClass<E>,
    preview: @Composable (E) -> Unit,
    content: @Composable (E) -> Unit
) {
    val store = LocalUiEffectStore.current
    OnInit {
        store.bindComposableWithUiEffect(
            kClass = kClass,
            effect = {
                val isPreview = LocalInspectionMode.current
                scope.uiEffect(kClass).value.ifEnabled {
                    if (!isPreview) {
                        content(it)
                    } else {
                        preview(it)
                    }
                }
            }
        )
    }
}