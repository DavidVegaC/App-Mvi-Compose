package com.davega.auth.ui.login

import androidx.lifecycle.SavedStateHandle
import com.davega.auth.domain.auth.use_cases.get_dni.GetDniUseCase
import com.davega.auth.domain.auth.use_cases.login.LoginParameters
import com.davega.auth.domain.auth.use_cases.login.LoginResult
import com.davega.auth.domain.auth.use_cases.login.LoginUseCase
import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.auth.domain.auth.use_cases.get_cipher.GetCipherUseCase
import com.davega.auth.domain.auth.use_cases.save_cipher.SaveCipherParameters
import com.davega.auth.domain.auth.use_cases.save_cipher.SaveCipherUseCase
import com.davega.auth.ui.login.dialogs.ConfigBiometricError
import com.davega.auth.ui.login.interactors.LoginUiEvent
import com.davega.auth.ui.login.interactors.LoginUiIntent
import com.davega.auth.ui.login.interactors.LoginUiState
import com.davega.auth.ui.utils.BiometricActions
import com.davega.auth.ui.utils.BiometricResult
import com.davega.ui.compose.stateful.execUiIntent
import com.davega.ui.utils.containsEmoji
import com.davega.ui.utils.isNumber
import com.davega.ui.utils.launch
import com.davega.ui.core.viewmodel.BaseViewModel
import com.davega.ui.core.viewmodel.handler.setUiState
import com.davega.ui.utils.navigate
import kotlinx.coroutines.delay

class LoginBaseViewModel(
    private val loginUseCase: LoginUseCase,
    private val getDniUseCase: GetDniUseCase,
    private val saveCipherUseCase: SaveCipherUseCase,
    private val getCipherUseCase: GetCipherUseCase
): BaseViewModel<LoginUiState, LoginUiIntent, LoginUiEvent>(
    savedStateHandle = SavedStateHandle(),
    defaultUiState = { LoginUiState() }
) {

    init {
        LoginUiIntent.GetDni.exec()
    }

    override suspend fun handleIntent(intent: LoginUiIntent) = when(intent) {
        LoginUiIntent.GetDni -> getDni()
        is LoginUiIntent.SetDni -> setDni(intent.dni)
        is LoginUiIntent.SetPassword -> setPassword(intent.password)
        LoginUiIntent.HandlerToggleVisibility -> toggleVisibility()
        LoginUiIntent.HandleRemember -> toggleRemember()
        is LoginUiIntent.SaveCipherValue -> saveCipherValue(intent.dni, intent.cipherValue)
        LoginUiIntent.GoToMain -> goToMain()
        is LoginUiIntent.DecryptPin -> decryptionInfo(intent.key, intent.value)
        is LoginUiIntent.Login -> login(intent.dni, intent.password, intent.remember)
        is LoginUiIntent.EncryptPin -> encryptPin(intent.biometricActions , intent.key, intent.value)
        is LoginUiIntent.Decryption -> decryptPin(intent.biometricActions , intent.key, intent.value)
    }

    private suspend fun goToMain() {
        isLoading = true
        delay(1000)
        isLoading = false
        LoginUiEvent.GoToMain.send()
    }

    private suspend fun getDni() {
        val rememberDni = getDniUseCase()
        if(rememberDni != null) {
            setUiState {
                copy(
                    dni = rememberDni,
                    remember = true
                )
            }
            getCipherValue(rememberDni)
        }
    }

    private fun getCipherValue(dni: String) = launch {
        val newCipherValue = getCipherUseCase(dni)
        setUiState {
            copy(
                cipherValue = if(uiState.dni == dni) newCipherValue else cipherValue
            )
        }
    }

    private fun setDni(value: String) {
        if(value.length <= 8 && value.isNumber()){
            setUiState {
                copy(
                    dni = value,
                    isError = false,
                    cipherValue = null
                )
            }
            if(value.length == 8){
                getCipherValue(dni = value)
            }
        }
    }

    private fun setPassword(value: String){
        if(value.containsEmoji().not()){
            setUiState {
                copy(
                    password = value,
                    isError = false
                )
            }
        }
    }

    private suspend fun login(dni: String, password: String, remember: Boolean) {
        if (isLoading && !uiState.isValid(dni, password)) {
            return
        }
        isLoading = true
        val result = loginUseCase(
            LoginParameters(
                dni = dni,
                password = password,
                remember = remember
            )
        )
        isLoading = false
        when(result){
            is LoginResult.Success -> {
                if(uiState.cipherValue != null){
                    LoginUiEvent.GoToMain.send()
                } else {
                    LoginUiEvent.EncryptPin(
                        key = dni,
                        value = password
                    ).send()
                }
            }
            is LoginResult.Error -> {
                setUiState {
                    copy(
                        isError = true
                    )
                }
            }
        }
    }

    private suspend fun encryptPin(biometricActionsNullable: BiometricActions?, dni: String, password: String) {
        biometricActionsNullable?.let { biometricActions ->
            val result = biometricActions.encryption(
                key = dni,
                value = password,
            )
            when(result) {
                is BiometricResult.SuccessEncryption -> {
                    saveCipherValue(dni, result.cipherValue)
                }
                is BiometricResult.Error -> {
                    LoginUiEvent.ShowConfigBiometricError.send()
                }
                is BiometricResult.Cancel -> {
                    LoginUiEvent.GoToMain.send()
                }
                else -> {}
            }
        } ?: LoginUiEvent.GoToMain.send()
    }

    private suspend fun decryptPin(biometricActionsNullable: BiometricActions?, dni: String, password: CiphertextWrapper) {
        biometricActionsNullable?.let { biometricActions ->
            val result = biometricActions.decryption(
                key = dni,
                value = password,
            )
            when(result) {
                is BiometricResult.SuccessDecryption -> {
                    login(
                        dni = dni,
                        password = result.value,
                        remember = uiState.remember
                    )
                }
                is BiometricResult.Error -> {
                    LoginUiEvent.ShowBiometricError.send()
                }
                is BiometricResult.Cancel -> {
                    LoginUiEvent.GoToMain.send()
                }
                else -> {}
            }
        } ?: LoginUiEvent.GoToMain.send()
    }

    private fun toggleVisibility(){
        setUiState {
            copy(
                isVisible = !isVisible
            )
        }
    }

    private fun toggleRemember(){
        setUiState {
            copy(
                remember = !remember
            )
        }
    }

    private fun saveCipherValue(
        dni: String,
        cipherValue: CiphertextWrapper,
    ) = launch {
        saveCipherUseCase(
            SaveCipherParameters(
                dni = dni,
                cipherValue = cipherValue
            )
        )
        LoginUiEvent.GoToMain.send()
    }

    private fun decryptionInfo(dni: String, cipherValue: CiphertextWrapper){
        LoginUiEvent.DecryptPin(
            key = dni,
            value = cipherValue
        ).send()
    }
}