package com.davega.ui.core.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.davega.ui.utils.navigate
import com.davega.ui.core.util.onBackPressedCallback

/**
 * Clase abstracta que representa un Fragment con características específicas de la plataforma.
 * Extiende la clase [Fragment]
 */
abstract class AppBaseFragment: Fragment() {

    /**
     * Callback para manejar la acción de retroceso (back) en el Fragment.
     */
    private val onBackPressedCallback by onBackPressedCallback()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Habilita el callback para el manejo de la acción de retroceso (back).
        onBackPressedCallback?.isEnabled = true
    }

    /**
     * Muestra un [DialogFragment] dentro del Fragment.
     *
     * @param tag El tag opcional que se utiliza para mostrar el [DialogFragment].
     */
    protected fun DialogFragment.show(tag: String? = null){
        if(!isAdded){
            this.show(this@AppBaseFragment.childFragmentManager, tag)
        }
    }

    /**
     * Extensión de [NavDirections] que permite navegar utilizando las configuraciones de navegación
     * definidas en el Fragment actual.
     */
    fun NavDirections.navigate(){
        this@AppBaseFragment.navigate(this)
    }
}