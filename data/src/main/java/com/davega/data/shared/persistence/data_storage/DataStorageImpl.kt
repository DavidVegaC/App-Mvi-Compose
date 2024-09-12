package com.davega.data.shared.persistence.data_storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataStorageImpl @Inject constructor(
    @ApplicationContext context: Context
) : DataStorage {

    private lateinit var sharedPreferences: SharedPreferences

    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override suspend fun setString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override suspend fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override suspend fun removeString(key: String) {
        editor.remove(key).apply()
    }

    companion object {
        private const val FILE_NAME = "app_sp"
    }

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}