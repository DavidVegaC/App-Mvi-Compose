package com.davega.auth.domain.use_cases.get_cipher

import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.auth.domain.repository.AuthRepository
import com.davega.domain.shared.base.SimpleUseCase
import javax.inject.Inject

class GetCipherUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SimpleUseCase.ParamsAndResult<String, CiphertextWrapper?> {

    override suspend fun invoke(params: String): CiphertextWrapper? {
        return authRepository.getCipherValue(
            dni = params
        )
    }

}