package com.davega.ui.core.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davega.ui.core.fragment.AppStatefulFragment
import kotlin.reflect.KClass

/**
 * Clase privada que implementa [Lazy] para crear y obtener instancias de ViewModels de forma diferida.
 *
 * @param V El tipo de ViewModel que se va a crear.
 * @property fragment El Fragment asociado a la creación del ViewModel.
 * @property storeOwner El identificador opcional del "store owner" al que está asociado el ViewModel.
 * @property clazz La clase del ViewModel [V] que se va a crear.
 */
private class AppViewModelsLazy<V: ViewModel>(
    private val fragment: Fragment,
    private val storeOwner: Int?,
    private val clazz: KClass<V>
): Lazy<V> {

    private var _value: V? = null

    /**
     * Obtiene o crea el ViewModel [V] asociado al Fragment de forma diferida.
     */
    override val value: V
        get() {
            if (!isInitialized()) {
                _value = if (storeOwner != null) {
                    val owner = fragment.findNavController().getViewModelStoreOwner(storeOwner)
                    ViewModelProvider(
                        owner,
                        fragment.defaultViewModelProviderFactory
                    )[clazz.java]
                } else {
                    ViewModelProvider(fragment)[clazz.java]
                }
            }
            return _value!!
        }

    /**
     * Verifica si el ViewModel [V] ya ha sido inicializado.
     */
    override fun isInitialized(): Boolean = _value != null

}

class ViewModelCreator<V: Any>(
    val clazz: KClass<V>,
    val navGraphId: Int? = null
)

/**
 * Función de extensión para [AppStatefulFragment] que crea una instancia de ViewModel de forma diferida.
 *
 * @param V El tipo de ViewModel que se va a crear.
 * @return Una instancia de [Lazy] que permite acceder al ViewModel [V] de forma diferida.
 * @throws Exception Si el objeto no es un Fragment.
 */
fun <V: ViewModel> Fragment.appViewModels(
    creatorFactory: () -> ViewModelCreator<V>
): Lazy<V> {
    val creator = creatorFactory()
    return AppViewModelsLazy(
        fragment = this,
        storeOwner = creator.navGraphId,
        clazz = creator.clazz
    )
}

inline fun <reified VM: ViewModel> createViewModel(
    navGraphId: Int? = null
): ViewModelCreator<VM> {
    return ViewModelCreator(VM::class, navGraphId)
}