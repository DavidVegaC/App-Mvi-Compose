package com.davega.products.domain.product.entities

import androidx.annotation.Keep

enum class Currency {
    @Keep
    PEN,
    @Keep
    USD;
    companion object {
        fun find(name: String): Currency {
            return values().find { it.name == name } ?: PEN
        }
    }
}