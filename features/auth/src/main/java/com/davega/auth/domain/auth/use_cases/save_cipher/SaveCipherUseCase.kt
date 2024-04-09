package com.davega.auth.domain.auth.use_cases.save_cipher

import com.davega.auth.domain.auth.repository.AuthRepository
import com.davega.domain.shared.base.SimpleUseCase

class SaveCipherUseCase(
    private val authRepository: AuthRepository
): SimpleUseCase.OnlyParams<SaveCipherParameters> {

    override suspend fun invoke(params: SaveCipherParameters) {
        authRepository.saveCipherValue(
            dni = params.dni,
            cipherValue = params.cipherValue
        )
    }

}