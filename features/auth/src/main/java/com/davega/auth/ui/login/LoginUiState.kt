package com.davega.auth.ui.login

import com.davega.domain.auth.entities.CiphertextWrapper

data class LoginUiState(
    val dni: String = "",
    val password: String = "",
    val remember: Boolean = false,
    val isError: Boolean = false,
    val isVisible: Boolean = false,
    val cipherValue: CiphertextWrapper? = null
) {
    val isValid = isValid(dni, password)

    fun isValid(dni: String, password: String): Boolean {
        return dni.length == 8 && password.length >= 3
    }
}