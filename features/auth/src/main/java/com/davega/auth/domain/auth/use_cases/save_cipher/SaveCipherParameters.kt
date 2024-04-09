package com.davega.auth.domain.auth.use_cases.save_cipher

import com.davega.domain.auth.entities.CiphertextWrapper

data class SaveCipherParameters(
    val dni: String,
    val cipherValue: CiphertextWrapper
)