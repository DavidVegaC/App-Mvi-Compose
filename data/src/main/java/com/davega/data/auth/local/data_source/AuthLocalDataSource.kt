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
            key = AUTH_KEY,
            value = JSON.stringify(auth),
        )

        if (dni == null) {
            localStorage.removeString(key = DNI_KEY)
            return
        }
        localStorage.setString(key = DNI_KEY, value = dni)
    }

    suspend fun getDni(): String? = localStorage.getString(DNI_KEY)

    suspend fun getAuth(): Auth? {
        val authString: String = localStorage.getString(key = AUTH_KEY) ?: return null
        return JSON.parse(authString)
    }

    suspend fun saveCipherValue(dni: String, value: CiphertextWrapper){
        localStorage.setString(dni, value = JSON.stringify(value))
    }

    suspend fun getCipherValue(dni: String): CiphertextWrapper? {
        val dniString: String = localStorage.getString(dni) ?: return null
        return JSON.parse(dniString)
    }

    suspend fun removeAuth() {
        localStorage.removeString(key = AUTH_KEY)
    }

    companion object {
        private const val AUTH_KEY = "auth_key"
        private const val DNI_KEY = "dni_key"
    }
}