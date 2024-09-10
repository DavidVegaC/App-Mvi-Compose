package com.davega.ui.core.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.davega.ui.core.activity.LoadingActivity
import com.davega.ui.core.viewmodel.handler.LoaderHandler
import com.davega.ui.lifecycle.base.WithLoadingEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Obtiene una referencia a la interfaz [LoadingActivity] desde la actividad que contiene el fragmento actual,
 * asegurándose de que la actividad implemente dicha interfaz.
 *
 * @return Una instancia de [LoadingActivity] obtenida desde la actividad contenedora.
 *
 * @throws ClassCastException Si la actividad no implementa la interfaz [LoadingActivity].
 */
internal fun Fragment.requireLoadingActivity(): LoadingActivity {
    val result = requireActivity()
    return if (result is LoadingActivity) {
        result
    } else {
        throw ClassCastException("Activity does not implement LoadingActivity.")
    }
}

/**
 * Muestra un estado de carga (loading) durante el tiempo especificado en milisegundos.
 *
 * @param timeMillis El tiempo en milisegundos durante el cual se mostrará el estado de carga.
 */
suspend fun Fragment.showLoading(timeMillis: Long) {
    isLoading = true
    delay(timeMillis)
    isLoading = false
}

/**
 * Obtiene o establece el estado de carga (loading) del fragmento actual a través de la actividad contenedora.
 * Si se establece en `true`, se indica que el fragmento está en estado de carga; si se establece en `false`, la carga ha finalizado.
 */
var Fragment.isLoading: Boolean
    get() = requireLoadingActivity().isLoading
    set(value) {
        if (value && !isLoading) {
            requireLoadingActivity().isLoading = true
        } else if (!value && isLoading) {
            requireLoadingActivity().isLoading = false
        }
    }

/**
 * Conecta un ViewModel que implementa [WithLoadingEvent] al estado de carga (loading) del fragmento actual.
 * Esta función se asegura de gestionar adecuadamente el estado de carga durante la conexión.
 *
 * @param viewModel El ViewModel que implementa [WithLoadingEvent].
 */
fun Fragment.connectLoadingEvent(viewModel: LoaderHandler){
    viewLifecycleOwner.lifecycleScope.launch {
        try {
            viewModel.onLoading {
                isLoading = it
            }
        } finally {
            if(!viewModel.isLoading){
                isLoading = false
            }
        }
    }
}