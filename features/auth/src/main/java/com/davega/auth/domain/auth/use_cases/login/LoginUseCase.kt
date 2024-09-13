package com.davega.auth.domain.auth.use_cases.login

import com.davega.auth.domain.auth.repository.AuthRepository
import com.davega.data.auth.utils.SessionTimer
import com.davega.domain.shared.base.SimpleUseCase
import com.davega.domain.shared.utils.DataResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionTimer: SessionTimer
): SimpleUseCase.ParamsAndResult<LoginParameters, LoginResult> {

    override suspend fun invoke(params: LoginParameters): LoginResult {
        val result = authRepository.login(
            dni = params.dni,
            password = params.password,
            remember = params.remember
        )
        return when(result){
            is DataResult.Success -> {
                sessionTimer.login()
                LoginResult.Success
            }
            is DataResult.Error -> LoginResult.Error
        }
    }

}