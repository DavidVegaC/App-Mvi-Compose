package com.davega.auth.domain.auth.use_cases.login

sealed class LoginResult {
    object Success: LoginResult()
    object Error: LoginResult()
}