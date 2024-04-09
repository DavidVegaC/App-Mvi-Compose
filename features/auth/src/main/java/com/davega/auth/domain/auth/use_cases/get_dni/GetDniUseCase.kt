package com.davega.auth.domain.auth.use_cases.get_dni

import com.davega.auth.domain.auth.repository.AuthRepository
import com.davega.domain.shared.base.SimpleUseCase

class GetDniUseCase(
    private val authRepository: AuthRepository
): SimpleUseCase.OnlyResult<String?> {

    override suspend fun invoke(): String? {
        return authRepository.getDni()
    }

}