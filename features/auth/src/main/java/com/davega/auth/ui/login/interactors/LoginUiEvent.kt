package com.davega.auth.ui.login.interactors

import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.ui.core.viewmodel.handler.UiEvent

sealed class LoginUiEvent: UiEvent {
    object GoToMain: LoginUiEvent()

    object ShowConfigBiometricError: LoginUiEvent()
    object ShowBiometricError: LoginUiEvent()
    class EncryptPin(val key: String, val value: String): LoginUiEvent()
    class DecryptPin(val key: String, val value: CiphertextWrapper): LoginUiEvent()
}