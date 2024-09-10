package com.davega.auth.ui.login.interactors

import com.davega.auth.ui.utils.BiometricActions
import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.ui.core.viewmodel.handler.UiIntent

sealed class LoginUiIntent: UiIntent {

    object GetDni: LoginUiIntent()
    class SetDni(val dni: String): LoginUiIntent()
    class SetPassword(val password: String): LoginUiIntent()
    object HandlerToggleVisibility: LoginUiIntent()
    object HandleRemember: LoginUiIntent()
    object GoToMain: LoginUiIntent()
    class SaveCipherValue(val dni: String, val cipherValue: CiphertextWrapper): LoginUiIntent()
    class DecryptPin(val key: String, val value: CiphertextWrapper): LoginUiIntent()
    class Login(val dni: String, val password: String, val remember: Boolean): LoginUiIntent()
    class EncryptPin(val biometricActions: BiometricActions?, val key: String, val value: String): LoginUiIntent()
    class Decryption(val biometricActions: BiometricActions?, val key: String, val value: CiphertextWrapper): LoginUiIntent()
}