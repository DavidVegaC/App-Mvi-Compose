package com.davega.auth.data.auth.remote.data_source

import com.davega.auth.data.auth.remote.api.AuthApi
import com.davega.auth.data.auth.remote.dto.request.AuthRequest
import com.davega.auth.data.auth.remote.dto.response.AuthResponse
import com.davega.data.shared.utils.safeApiCall
import com.davega.domain.shared.utils.DataResult

class AuthRemoteDataSource(
    private val api: AuthApi
) {

    suspend fun login(auth: AuthRequest): DataResult<AuthResponse> {
        return safeApiCall {
            api.login(auth)
        }
    }

}