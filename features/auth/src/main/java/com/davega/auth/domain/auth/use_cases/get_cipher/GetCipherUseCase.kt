package com.davega.auth.domain.auth.use_cases.get_cipher

import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.auth.domain.auth.repository.AuthRepository
import com.davega.domain.shared.base.SimpleUseCase

class GetCipherUseCase(
    private val authRepository: AuthRepository
): SimpleUseCase.ParamsAndResult<String, CiphertextWrapper?> {

    override suspend fun invoke(params: String): CiphertextWrapper? {
        return authRepository.getCipherValue(
            dni = params
        )
    }

}