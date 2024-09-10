package com.davega.ui.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davega.ui.lifecycle.base.WithUiState

/**
 * Clase abstracta que representa un ViewModel con capacidad para gestionar el estado de interfaz de usuario (UI).
 *
 * Esta clase proporciona funcionalidad para gestionar el estado de interfaz de usuario y eventos de interfaz de usuario.
 *
 * @param UiState El tipo de estado de interfaz de usuario que maneja este ViewModel.
 * @param UiEvent El tipo de evento de interfaz de usuario que maneja este ViewModel.
 * @param defaultUiEvent El evento de interfaz de usuario por defecto (opcional) que se utilizará al inicializar el ViewModel.
 * @param defaultUiState El estado de interfaz de usuario por defecto que se utilizará al inicializar el ViewModel.
 */
abstract class StatefulViewModel<UiState, UiEvent>(
    defaultUiEvent: UiEvent? = null,
    defaultUiState: UiState
): StatelessViewModel<UiEvent>(
    defaultUiEvent = defaultUiEvent
), WithUiState<UiState> {

    /**
     * LiveData que proporciona cambios en el estado de interfaz de usuario. Cada emisión es un par que contiene el
     * estado anterior y el estado actual.
     */
    private val _changeUiStateLiveData = MutableLiveData<Pair<UiState?, UiState>>(null to defaultUiState)
    /**
     * LiveData que proporciona el estado de interfaz de usuario actual.
     */
    private val _uiStateLiveData = MutableLiveData(defaultUiState)

    override val changeUiStateLiveData: LiveData<Pair<UiState?, UiState>>
        get() = _changeUiStateLiveData

    override val uiStateLiveData: LiveData<UiState>
        get() = _uiStateLiveData

    override val uiState: UiState get() = uiStateLiveData.value!!

    override fun setUiState(uiState: UiState) {
        _changeUiStateLiveData.value = _uiStateLiveData.value to uiState
        _uiStateLiveData.value = uiState
    }

}