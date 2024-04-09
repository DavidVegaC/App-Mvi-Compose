package com.davega.auth.domain.auth.repository

import com.davega.domain.auth.entities.CiphertextWrapper
import com.davega.domain.auth.entities.Auth
import com.davega.domain.shared.utils.DataResult

interface AuthRepository {

    suspend fun login(dni: String, password: String, remember: Boolean): DataResult<Auth>

    suspend fun getDni(): String?

    suspend fun saveCipherValue(dni: String, cipherValue: CiphertextWrapper)

    suspend fun getCipherValue(dni: String): CiphertextWrapper?

}