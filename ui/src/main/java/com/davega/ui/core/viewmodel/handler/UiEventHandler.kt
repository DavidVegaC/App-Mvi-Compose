package com.davega.ui.core.viewmodel.handler

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

interface UiEvent

interface UiEventHandler<E: UiEvent> {

    fun E.send()

    fun sendUiEvent(event: E)
    suspend fun setOnUiEvent(handle: suspend (E) -> Unit)
}

private class DefaultEventHandler<E: UiEvent>: UiEventHandler<E> {

    private val _event = Channel<E>()

    override fun sendUiEvent(event: E) {
        _event.trySend(event)
    }

    override fun E.send() {
        sendUiEvent(this)
    }

    override suspend fun setOnUiEvent(handle: suspend (E) -> Unit) {
        _event.receiveAsFlow().collectLatest {
            handle(it)
        }
    }

}

fun <E: UiEvent> uiEventHandler(): UiEventHandler<E> {
    return DefaultEventHandler()
}