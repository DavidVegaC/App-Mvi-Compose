package com.davega.ui.lifecycle

import androidx.lifecycle.ViewModel
import com.davega.ui.lifecycle.base.WithLoadingEvent
import com.davega.ui.lifecycle.base.WithUiEvent
import com.davega.ui.utils.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Clase abstracta que representa un ViewModel sin estado, diseñado para manejar eventos de interfaz de usuario (UI) y
 * eventos de carga (loading).
 *
 * @param UiEvent El tipo de evento de interfaz de usuario que maneja este ViewModel.
 * @param defaultUiEvent El evento de interfaz de usuario por defecto (opcional) que se utilizará al inicializar el ViewModel.
 */
abstract class StatelessViewModel<UiEvent>(
    defaultUiEvent: UiEvent? = null
): ViewModel(), WithUiEvent<UiEvent>, WithLoadingEvent {

    /**
     * Canal (Channel) utilizado para recibir eventos de interfaz de usuario.
     */
    private val eventChannel = Channel<UiEvent>(Channel.BUFFERED)

    /**
     * Estado de carga actual representado como un flujo mutable (MutableStateFlow).
     */
    private val _isLoading = MutableStateFlow(false)

    override var isLoading
        get(): Boolean {
            return _isLoading.value
        }
        set(value) {
            _isLoading.tryEmit(value)
        }

    override suspend fun setOnUiEvent(onEvent: suspend (UiEvent) -> Unit){
        eventChannel.receiveAsFlow().collectLatest(onEvent)
    }

    final override fun UiEvent.send(){
        eventChannel.trySendBlocking(this)
    }

    override suspend fun onLoading(listener: (isLoading: Boolean) -> Unit){
        _isLoading.collectLatest {
            listener(it)
        }
    }

    init {
        // Envía el evento de interfaz de usuario por defecto, si se proporciona.
        defaultUiEvent?.send()
        // Lanza la función de inicialización asincrónica.
        launch {
            onInit()
        }
    }

    /**
     * Función que puede ser anulada en las subclases para realizar acciones de inicialización personalizadas.
     * Se ejecuta de manera asincrónica después de la creación del ViewModel.
     */
    open suspend fun onInit(){}

}