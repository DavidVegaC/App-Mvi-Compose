package com.davega.ui.core.fragment

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

/**
 * Interfaz que define un Fragment que está asociado a un ViewModel de tipo [V] y opcionalmente a un "store owner".
 *
 * @param V El tipo de ViewModel asociado al Fragment.
 * @property clazz La clase del ViewModel [V] asociado al Fragment.
 * @property storeOwner Un identificador opcional para el "store owner" al que está asociado el Fragment.
 */
interface AppStatefulFragment<V: ViewModel> {
    /**
     * La clase del ViewModel [V] asociado al Fragment.
     */
    val clazz: KClass<V>

    /**
     * Un identificador opcional para el "store owner" al que está asociado el Fragment.
     */
    val storeOwner: Int?
}