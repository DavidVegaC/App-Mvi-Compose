package com.davega.ui.core.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

/**
 * Clase abstracta que sirve como base para las actividades de la aplicación.
 * Extiende [AppCompatActivity] para proporcionar funcionalidades comunes.
 *
 * Esta clase abstracta proporciona métodos para mostrar y ocultar fragmentos de diálogo.
 */
abstract class AppBaseActivity: AppCompatActivity() {

    /**
     * Muestra un fragmento de diálogo.
     *
     * @param tag Una etiqueta opcional para identificar el fragmento en la gestión del fragmento.
     */
    protected fun DialogFragment.show(tag: String? = null) {
        try {
            if (!isAdded) {
                show(supportFragmentManager, tag)
            }
        } catch (_: Exception) {}
    }

    /**
     * Oculta un fragmento de diálogo.
     */
    protected fun DialogFragment.hide() {
        try {
            dismissAllowingStateLoss()
        } catch (_: Exception) {}
    }

}