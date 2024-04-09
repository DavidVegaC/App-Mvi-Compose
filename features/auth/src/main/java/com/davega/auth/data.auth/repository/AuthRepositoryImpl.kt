package com.davega.auth.data.auth.repository

import com.davega.data.auth.local.data_source.AuthLocalDataSource
import com.davega.auth.data.auth.remote.data_source.AuthRemoteDataSource
import com.davega.auth.data.auth.remote.dto.request.AuthRequest
import com.davega.auth.data.auth.repository.mappers.toAuth
import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.domain.auth.entities.Auth
import com.davega.auth.domain.auth.repository.AuthRepository
import com.davega.domain.shared.utils.DataResult

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource,
    private val local: AuthLocalDataSource
): AuthRepository {

    override suspend fun login(dni: String, password: String, remember: Boolean): DataResult<Auth> {
        val result = remote.login(
            AuthRequest(
                dni = dni,
                password = password
            )
        ).map {
            it.toAuth()
        }
        if(result is DataResult.Success){
            local.saveAuth(
                dni = if(remember) dni else null,
                auth = result.data
            )
        }
        return result
    }

    override suspend fun getDni(): String? {
        return local.getDni()
    }

    override suspend fun getCipherValue(dni: String): CiphertextWrapper? {
        return local.getCipherValue(dni)
    }

    override suspend fun saveCipherValue(dni: String, cipherValue: CiphertextWrapper) {
        local.saveCipherValue(
            dni = dni,
            value = cipherValue
        )
    }

}