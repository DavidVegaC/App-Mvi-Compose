package com.davega.auth.ui.login

import com.davega.auth.domain.auth.use_cases.get_dni.GetDniUseCase
import com.davega.auth.domain.auth.use_cases.login.LoginParameters
import com.davega.auth.domain.auth.use_cases.login.LoginResult
import com.davega.auth.domain.auth.use_cases.login.LoginUseCase
import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.auth.domain.auth.use_cases.get_cipher.GetCipherUseCase
import com.davega.auth.domain.auth.use_cases.save_cipher.SaveCipherParameters
import com.davega.auth.domain.auth.use_cases.save_cipher.SaveCipherUseCase
import com.davega.ui.lifecycle.StatefulViewModel
import com.davega.ui.utils.containsEmoji
import com.davega.ui.utils.isNumber
import com.davega.ui.utils.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val getDniUseCase: GetDniUseCase,
    private val saveCipherUseCase: SaveCipherUseCase,
    private val getCipherUseCase: GetCipherUseCase
): StatefulViewModel<LoginUiState, LoginUiEvent>(
    state = LoginUiState()
) {

    override suspend fun onInit() {
        val rememberDni = getDniUseCase()
        if(rememberDni != null){
            setUiState {
                copy(
                    dni = rememberDni,
                    remember = true
                )
            }
            getCipherValue(rememberDni)
        }
    }

    fun setDni(value: String){
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

    fun setPassword(value: String){
        if(!value.containsEmoji()){
            setUiState {
                copy(
                    password = value,
                    isError = false
                )
            }
        }
    }

    fun login(dni: String, password: String, remember: Boolean) = launch {
        if (loading && !uiState.isValid(dni, password)) {
            return@launch
        }
        loading = true
        val result = loginUseCase(
            LoginParameters(
                dni = dni,
                password = password,
                remember = remember
            )
        )
        loading = false
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

    fun toggleVisibility(){
        setUiState {
            copy(
                isVisible = !isVisible
            )
        }
    }

    fun toggleRemember(){
        setUiState {
            copy(
                remember = !remember
            )
        }
    }

    fun saveCipherValue(
        dni: String,
        cipherValue: CiphertextWrapper
    ) = launch {
        saveCipherUseCase(
            SaveCipherParameters(
                dni = dni,
                cipherValue = cipherValue
            )
        )
        LoginUiEvent.GoToMain.send()
    }

    fun decryptionInfo(dni: String, cipherValue: CiphertextWrapper){
        LoginUiEvent.DecryptPin(
            key = dni,
            value = cipherValue
        ).send()
    }

    private fun getCipherValue(dni: String) = launch {
        val newCipherValue = getCipherUseCase(dni)
        setUiState {
            copy(
                cipherValue = if(uiState.dni == dni) newCipherValue else cipherValue
            )
        }
    }

}