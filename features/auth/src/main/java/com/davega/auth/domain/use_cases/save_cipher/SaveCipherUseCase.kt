package com.davega.auth.domain.use_cases.save_cipher

import com.davega.auth.domain.repository.AuthRepository
import com.davega.domain.shared.base.SimpleUseCase
import javax.inject.Inject

class SaveCipherUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SimpleUseCase.OnlyParams<SaveCipherParameters> {

    override suspend fun invoke(params: SaveCipherParameters) {
        authRepository.saveCipherValue(
            dni = params.dni,
            cipherValue = params.cipherValue
        )
    }

}