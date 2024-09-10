package com.davega.ui.lifecycle.base

import androidx.lifecycle.LiveData

/**
 * Interfaz genérica que define la capacidad de un componente para gestionar el estado de interfaz de usuario (UI).
 *
 * Esta interfaz permite a un componente proporcionar y gestionar el estado de la interfaz de usuario y notificar a los
 * observadores sobre cambios en dicho estado.
 *
 * @param UiState El tipo de estado de interfaz de usuario que este componente puede manejar.
 */
interface WithUiState<UiState> {
    /**
     * LiveData que proporciona el estado de interfaz de usuario actual.
     */
    val uiStateLiveData: LiveData<UiState>

    /**
     * LiveData que proporciona cambios en el estado de interfaz de usuario. Cada emisión es un par que contiene el
     * estado anterior y el estado actual.
     */
    val changeUiStateLiveData: LiveData<Pair<UiState?, UiState>>

    /**
     * Propiedad que representa el estado de interfaz de usuario actual.
     */
    val uiState: UiState

    /**
     * Función que permite establecer el estado de interfaz de usuario.
     *
     * @param uiState El nuevo estado de interfaz de usuario que se establecerá.
     */
    fun setUiState(uiState: UiState)

}