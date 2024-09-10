package com.davega.ui.core.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Ejecuta una suspensión [block] en el [viewModelScope] del ViewModel actual.
 *
 * @param context El [CoroutineDispatcher] en el que se ejecutará el [block]. Por defecto, utiliza [Dispatchers.Main].
 * @param block El bloque de código suspendido que se ejecutará en el [viewModelScope].
 *
 * Esta función simplifica la creación y gestión de corutinas dentro de un ViewModel,
 * utilizando el [viewModelScope] para garantizar que las corutinas se cancelen correctamente cuando el ViewModel es desechado.
 */
fun ViewModel.launch(
    context: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(block = block, context = context)
}