package com.davega.auth.domain.use_cases.login

sealed class LoginResult {
    object Success: LoginResult()
    object Error: LoginResult()
}