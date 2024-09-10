package com.davega.ui.core.activity

/**
 * Interfaz que define una actividad con capacidad para gestionar el estado de carga (loading).
 *
 * Esta interfaz se utiliza para representar actividades que pueden cambiar su estado de carga.
 */
interface LoadingActivity {
    /**
     * Obtiene o establece el estado de carga de la actividad.
     *
     * @property isLoading Un valor booleano que indica si la actividad está en estado de carga.
     * Si es `true`, la actividad está en proceso de carga; si es `false`, la carga ha finalizado.
     */
    var isLoading: Boolean
}