package com.davega.ui.compose.stateful.effect

import androidx.compose.runtime.Composable
import com.davega.ui.compose.stateful.LocalStateful
import com.davega.ui.compose.stateful.Stateful

object GlobalUiEffectProvider {

    private var registerUiEffect: (@Composable Stateful<*>.() -> Unit)? = null
    
    operator fun invoke(
        effect: @Composable Stateful<*>.() -> Unit
    ) {
        this.registerUiEffect = effect
    }

    @Composable
    fun Register() {
        val scope = LocalStateful.current
        registerUiEffect?.invoke(scope)
    }
}