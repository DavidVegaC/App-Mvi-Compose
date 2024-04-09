package com.davega.auth.domain.auth.use_cases.login

data class LoginParameters(
    val dni: String,
    val password: String,
    val remember: Boolean
)