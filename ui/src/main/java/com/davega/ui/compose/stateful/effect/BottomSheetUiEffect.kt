package com.davega.ui.compose.stateful.effect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.davega.ui.compose.stateful.Stateful
import com.davega.ui.compose.stateful.core.disableUiEffect
import com.davega.ui.compose.stateful.findViewModel
import com.davega.ui.core.viewmodel.handler.UiEffect
import com.davega.ui.core.viewmodel.handler.ifEnabled
import com.davega.ui.core.viewmodel.handler.isEnabled
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

interface UiEffectBottomSheetScope {
    fun dismiss()

    companion object {

        @OptIn(ExperimentalMaterial3Api::class)
        fun create(
            sheetState: SheetState,
            coroutineScope: CoroutineScope,
            onDismissRequest: () -> Unit
        ): UiEffectBottomSheetScope {
            return DefaultUiEffectBottomSheetScope(
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                onDismissRequest = onDismissRequest
            )
        }

        fun createMock(): UiEffectBottomSheetScope {
            return MockUiEffectBottomSheetScope()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private class DefaultUiEffectBottomSheetScope(
    private val sheetState: SheetState,
    private val coroutineScope: CoroutineScope,
    private val onDismissRequest: () -> Unit
): UiEffectBottomSheetScope {

    override fun dismiss() {
        coroutineScope.launch {
            sheetState.hide()
            onDismissRequest()
        }
    }
}

private class MockUiEffectBottomSheetScope: UiEffectBottomSheetScope {
    override fun dismiss() {
        // no-op
    }
}

@Composable
fun BottomSheetContainerPreview(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.3f)
            ),
        contentAlignment = Alignment.BottomStart
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <E: UiEffect> BottomSheetUiEffect(
    scope: Stateful<*>,
    kClass: KClass<E>,
    isCancelable: Boolean = true,
    content: @Composable UiEffectBottomSheetScope.(E) -> Unit
) {
    ModalUiEffect(
        scope = scope,
        kClass = kClass,
        preview = { uiEffect ->
            val modalScope = remember {
                UiEffectBottomSheetScope.createMock()
            }
            BottomSheetContainerPreview {
                content.invoke(modalScope, uiEffect)
            }
        }
    ) { uiEffect ->
        val sheetState: SheetState = rememberModalBottomSheetState(
            confirmValueChange = { isCancelable }
        )
        var isShowBottomSheet by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(uiEffect) {
            isShowBottomSheet = uiEffect.isEnabled()
        }
        if (isShowBottomSheet) {
            val coroutineScope = rememberCoroutineScope()
            val modalScope = remember {
                UiEffectBottomSheetScope.create(
                    sheetState = sheetState,
                    coroutineScope = coroutineScope,
                ) {
                    disableUiEffect(
                        handler = scope.findViewModel(),
                        kClass = kClass
                    )
                }
            }
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    modalScope.dismiss()
                },
                containerColor = Color.Transparent,
                dragHandle = null,
                shape = RectangleShape,
            ) {
                uiEffect.ifEnabled {
                    content.invoke(modalScope, it)
                }
            }
        }
    }
}

@Composable
inline fun <reified E: UiEffect> Stateful<*>.BottomSheetUiEffect(
    isCancelable: Boolean = true,
    noinline content: @Composable UiEffectBottomSheetScope.(E) -> Unit
) {
    BottomSheetUiEffect(
        scope = this,
        kClass = E::class,
        isCancelable = isCancelable,
        content = content
    )
}
