package com.davega.products.domain.movement.entities

enum class MovementType {
    INPUT,
    OUTPUT;
    companion object {
        fun find(name: String): MovementType {
            return values().find { it.name.lowercase() == name } ?: INPUT
        }
    }
}