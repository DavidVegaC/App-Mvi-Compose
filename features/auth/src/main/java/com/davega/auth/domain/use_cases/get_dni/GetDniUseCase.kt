package com.davega.auth.domain.use_cases.get_dni

import com.davega.auth.domain.repository.AuthRepository
import com.davega.domain.shared.base.SimpleUseCase
import javax.inject.Inject

class GetDniUseCase  @Inject constructor(
    private val authRepository: AuthRepository
): SimpleUseCase.OnlyResult<String?> {

    override suspend fun invoke(): String? {
        return authRepository.getDni()
    }

}