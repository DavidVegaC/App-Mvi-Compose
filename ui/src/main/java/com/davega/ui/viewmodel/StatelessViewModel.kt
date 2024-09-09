package com.davega.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.davega.ui.utils.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

abstract class StatelessViewModel<Event>(
    event: Event? = null
): ViewModel(), LoadingEvent {

    protected var loading by mutableStateOf(false)

    private var listener: (suspend CoroutineScope.(Event) -> Unit)? = null

    private val eventChannel = Channel<Event>(Channel.BUFFERED)

    @Composable
    fun OnEvent(onEvent: suspend (Event) -> Unit){
        LaunchedEffect(Unit){
            eventChannel.receiveAsFlow().collectLatest {
                onEvent(it)
            }
        }
    }

    @Composable
    override fun OnLoading(listener: suspend CoroutineScope.(isLoading: Boolean) -> Unit){
        LaunchedEffect(loading){
            listener(loading)
        }
    }

    override fun isLoading(): Boolean = loading

    override fun loading(isLoading: Boolean) {
        loading = isLoading
    }

    override fun loading(isLoading: Boolean, timeMillis: Long) {
        launch {
            loading = isLoading
            if(loading){
                delay(timeMillis)
                loading = false
            }
        }
    }

    init {
        event?.let {
            sendEvent(it)
        }
        launch {
            onInit()
        }
    }

    private fun sendEvent(event: Event) {
        launch {
            eventChannel.send(event)
        }
    }

    protected fun Event.send(){
        sendEvent(this)
    }

    open suspend fun onInit(){}

}