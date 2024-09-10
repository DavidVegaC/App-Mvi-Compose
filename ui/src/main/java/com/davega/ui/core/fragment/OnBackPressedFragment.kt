package com.davega.ui.core.fragment

/**
 * Interfaz que define un método para gestionar el evento de retroceso (onBackPressed) en un fragmento.
 *
 * Esta interfaz se utiliza para proporcionar una forma de gestionar el evento de retroceso en fragmentos.
 */
interface OnBackPressedFragment {

    /**
     * Método llamado cuando se detecta el evento de retroceso (onBackPressed) en el fragmento.
     * Por defecto, este método no realiza ninguna acción.
     *
     * Puedes implementar este método en tu fragmento para personalizar el comportamiento
     * cuando se presiona el botón de retroceso en dicho fragmento.
     */
    fun onBackPressed() {}

}