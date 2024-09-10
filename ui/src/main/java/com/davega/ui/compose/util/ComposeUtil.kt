package com.davega.ui.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope

private val LocalFragment = staticCompositionLocalOf<Fragment> {
    error("No Fragment provided")
}

@Composable
fun localFragment() = LocalFragment.current

@Composable
fun Fragment.FragmentProvider(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalFragment provides this,
    ) {
        content()
    }
}

@Composable
fun OnInit(
    block: suspend CoroutineScope.() -> Unit
) {
    LaunchedEffect(Unit, block)
}