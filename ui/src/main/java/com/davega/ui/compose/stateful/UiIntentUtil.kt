package com.davega.ui.compose.stateful

import androidx.lifecycle.ViewModel
import com.davega.ui.core.viewmodel.handler.UiIntent
import com.davega.ui.core.viewmodel.handler.UiIntentHandler

fun <I: UiIntent, V> Stateful<V>.execUiIntent(intent: I)
where  V: ViewModel, V: UiIntentHandler<I> {
    findViewModel().execUiIntent(intent)
}