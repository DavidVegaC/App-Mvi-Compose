package com.davega.auth.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("dni")
    val dni: String,
    @SerializedName("password")
    val password: String
)