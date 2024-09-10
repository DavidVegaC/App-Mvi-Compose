package com.davega.auth.ui.login.interactors

import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.ui.core.viewmodel.handler.UiState

data class LoginUiState(
    val dni: String = "",
    val password: String = "",
    val remember: Boolean = false,
    val isError: Boolean = false,
    val isVisible: Boolean = false,
    val cipherValue: CiphertextWrapper? = null
): UiState {
    val isValid = isValid(dni, password)

    fun isValid(dni: String, password: String): Boolean {
        return dni.length == 8 && password.length >= 3
    }
}