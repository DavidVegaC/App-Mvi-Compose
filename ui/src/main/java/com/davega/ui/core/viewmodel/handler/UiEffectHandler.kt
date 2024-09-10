package com.davega.ui.core.viewmodel.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

interface UiEffect

interface UiEffectHandler {

    fun <E: UiEffect> registerUiEffect(kClass: KClass<E>): Flow<E?>

    fun <E: UiEffect> getUiEffect(kClass: KClass<E>): E?

    fun <E: UiEffect> isUiEffectEnabled(kClass: KClass<E>): Boolean

    fun <E: UiEffect> enableUiEffect(kClass: KClass<E>, value: E)

    fun <E: UiEffect> disableUiEffect(kClass: KClass<E>)

    fun <E: UiEffect> enableUiEffect(kClass: KClass<E>, value: E, timeMillis: Long)

    fun deactivateAllUiEffects()
}

inline fun <reified E: UiEffect> UiEffectHandler.registerUiEffect(): Flow<E?> {
    return registerUiEffect(E::class)
}

inline fun <reified E: UiEffect> UiEffectHandler.getUiEffect(): E? {
    return getUiEffect(E::class)
}

inline fun <reified E: UiEffect> UiEffectHandler.isUiEffectEnabled(): Boolean {
    return isUiEffectEnabled(E::class)
}

inline fun <reified E: UiEffect> UiEffectHandler.enableUiEffect(value: E) {
    enableUiEffect(E::class, value)
}

inline fun <reified E: UiEffect> UiEffectHandler.disableUiEffect() {
    disableUiEffect(E::class)
}
inline fun <reified E: UiEffect> UiEffectHandler.enableUiEffect(value: E, timeMillis: Long) {
    enableUiEffect(E::class, value, timeMillis)
}

private class DefaultUiEffectHandler: UiEffectHandler {

    private val lastJobs = mutableMapOf<KClass<*>, Job>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val effects: MutableMap<KClass<*>, MutableStateFlow<Any?>> = mutableMapOf()

    private fun <E: UiEffect> internalRegisterEffect(kClass: KClass<E>): MutableStateFlow<Any?> {
        return effects.getOrPut(kClass) { MutableStateFlow(value = null) }
    }

    override fun <E: UiEffect> registerUiEffect(kClass: KClass<E>): Flow<E?> {
        return internalRegisterEffect(kClass).map { it as E? }
    }

    override fun <E : UiEffect> getUiEffect(kClass: KClass<E>): E? {
        return effects[kClass]?.value as E?
    }

    override fun <E : UiEffect> isUiEffectEnabled(kClass: KClass<E>): Boolean {
        return getUiEffect(kClass) != null
    }

    override fun <E : UiEffect> enableUiEffect(kClass: KClass<E>, value: E) {
        if (isUiEffectEnabled(kClass)) return
        internalRegisterEffect(kClass).value = value
    }

    override fun <E : UiEffect> disableUiEffect(kClass: KClass<E>) {
        if (!isUiEffectEnabled(kClass)) return
        internalRegisterEffect(kClass).value = null
    }

    override fun <E : UiEffect> enableUiEffect(kClass: KClass<E>, value: E, timeMillis: Long) {
        lastJobs[kClass]?.cancel()
        lastJobs[kClass] = coroutineScope.launch {
            enableUiEffect(kClass, value)
            delay(timeMillis)
            disableUiEffect(kClass)
        }
    }

    override fun deactivateAllUiEffects() {
        effects.forEach { (_, effect) ->
            effect.value = false
        }
    }
}

fun uiEffectHandler(): UiEffectHandler {
    return DefaultUiEffectHandler()
}

fun <E: UiEffect> E?.isEnabled(): Boolean = this != null

inline fun <E: UiEffect> E?.ifEnabled(
    block: (E) -> Unit
): E? {
    if (this != null) {
        block(this)
    }
    return this
}

inline fun <E: UiEffect> E?.ifDisabled(
    block: () -> Unit
): E? {
    if (this == null) {
        block()
    }
    return this
}