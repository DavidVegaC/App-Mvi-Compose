package com.davega.data.auth.local.data_source

import com.davega.data.shared.persistence.data_storage.DataStorage
import com.davega.data.shared.utils.JSON
import com.davega.domain.auth.entities.Auth
import com.davega.domain.auth.entities.CiphertextWrapper
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val localStorage: DataStorage
) {

    suspend fun saveAuth(dni: String?, auth: Auth){
        localStorage.setString(
            key = authKey,
            value = JSON.stringify(auth)
        )
        dni?.let {
            localStorage.setString(
                key = dniKey,
                value = it
            )
        } ?: localStorage.removeString(key = dniKey)
    }

    suspend fun getDni(): String? = localStorage.getString(dniKey)

    suspend fun getAuth(): Auth? {
        return localStorage.getString(key = authKey)?.let {
            JSON.parse(it)
        }
    }

    suspend fun saveCipherValue(dni: String, value: CiphertextWrapper){
        localStorage.setString(dni, value = JSON.stringify(value))
    }

    suspend fun getCipherValue(dni: String): CiphertextWrapper? {
        return localStorage.getString(dni)?.let {
            JSON.parse(it)
        }
    }

    suspend fun removeAuth() {
        localStorage.removeString(key = authKey)
    }

    companion object {
        private const val authKey = "auth_key"
        private const val dniKey = "dni_key"
    }

}