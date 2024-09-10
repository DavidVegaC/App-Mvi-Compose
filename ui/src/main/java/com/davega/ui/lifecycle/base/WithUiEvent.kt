package com.davega.ui.lifecycle.base

/**
 * Interfaz genérica que define la capacidad de un componente para gestionar eventos de interfaz de usuario (UI).
 *
 * Esta interfaz permite a un componente registrar oyentes para eventos de interfaz de usuario específicos y enviar dichos eventos.
 *
 * @param UiEvent El tipo de evento de interfaz de usuario que este componente puede manejar.
 */
interface WithUiEvent<UiEvent> {

    /**
     * Función suspendida que permite registrar un oyente para eventos de interfaz de usuario específicos.
     *
     * @param onEvent El bloque suspendido que será llamado cuando se produzca un evento de interfaz de usuario.
     */
    suspend fun setOnUiEvent(onEvent: suspend (UiEvent) -> Unit)

    /**
     * Función que permite enviar un evento de interfaz de usuario.
     *
     * Esta función permite que el componente emita un evento de interfaz de usuario a los oyentes registrados.
     *
     * @param event El evento de interfaz de usuario que se enviará a los oyentes registrados.
     */
    fun UiEvent.send()

}