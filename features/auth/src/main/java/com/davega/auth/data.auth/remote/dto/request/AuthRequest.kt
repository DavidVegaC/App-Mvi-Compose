package com.davega.auth.data.auth.remote.dto.request

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("dni")
    val dni: String,
    @SerializedName("password")
    val password: String
)