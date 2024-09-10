package com.davega.ui.core.viewmodel.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiIntent

interface UiIntentHandler<I: UiIntent> {

    fun I.exec()

    fun execUiIntent(intent: I)

    fun initialize(scope: CoroutineScope, handle: suspend (intent: I) -> Unit)
}

private class DefaultIntentHandler<I: UiIntent>: UiIntentHandler<I> {

    private val _intent = Channel<I>()

    override fun execUiIntent(intent: I) {
        _intent.trySend(intent)
    }

    override fun I.exec() {
        execUiIntent(this)
    }

    override fun initialize(scope: CoroutineScope, handle: suspend (intent: I) -> Unit) {
        scope.launch(Dispatchers.IO) {
            _intent.receiveAsFlow().collectLatest { intent ->
                scope.launch(Dispatchers.Main) {
                    handle(intent)
                }
            }
        }
    }
}

fun <I: UiIntent> uiIntentHandler(): UiIntentHandler<I> {
    return DefaultIntentHandler()
}