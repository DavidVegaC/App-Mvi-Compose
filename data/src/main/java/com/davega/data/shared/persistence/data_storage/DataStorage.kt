package com.davega.data.shared.persistence.data_storage

interface DataStorage {
    suspend fun setString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun removeString(key: String)
}