package com.davega.auth.ui.login

import com.davega.domain.auth.entities.CiphertextWrapper

sealed class LoginUiEvent {
    object GoToMain: LoginUiEvent()
    class EncryptPin(
        val key: String,
        val value: String
    ): LoginUiEvent()
    class DecryptPin(
        val key: String,
        val value: CiphertextWrapper
    ): LoginUiEvent()
}