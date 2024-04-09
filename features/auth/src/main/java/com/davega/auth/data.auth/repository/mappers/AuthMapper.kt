package com.davega.auth.data.auth.repository.mappers

import com.davega.auth.data.auth.remote.dto.response.AuthResponse
import com.davega.domain.auth.entities.Auth

fun AuthResponse.toAuth(): Auth {
    return Auth(
        token = token,
        type = type
    )
}