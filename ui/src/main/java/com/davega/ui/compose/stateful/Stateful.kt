package com.davega.ui.compose.stateful

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.davega.ui.compose.stateful.effect.GlobalUiEffectProvider
import com.davega.ui.compose.stateful.effect.LocalUiEffectStore
import com.davega.ui.compose.stateful.effect.UiEffectStoreProvider
import com.davega.ui.compose.util.localFragment
import com.davega.ui.core.util.ViewModelCreator
import com.davega.ui.core.util.appViewModels
import com.davega.ui.core.util.connectLoadingEvent
import com.davega.ui.core.viewmodel.handler.LoaderHandler
import kotlin.reflect.KClass

val LocalStateful = staticCompositionLocalOf<Stateful<*>> {
    error("No Stateful provided")
}

@Composable
fun StatefulProvider(
    stateful: Stateful<*>,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalStateful provides stateful
    ) {
        content()
    }
}

abstract class Stateful<V: ViewModel> {

    companion object {

        fun <V: ViewModel> create(
            fragment: Fragment,
            clazz: KClass<V>,
            navGraphId: Int? = null
        ): Stateful<V> {
            return DefaultStateful(
                fragment = fragment,
                clazz = clazz,
                navGraphId = navGraphId
            )
        }

        fun <V: ViewModel> preview(): Stateful<V> {
            return PreviewStateful()
        }
    }
}

private class DefaultStateful<V: ViewModel>(
    fragment: Fragment,
    clazz: KClass<V>,
    navGraphId: Int? = null
): Stateful<V>() {

    val viewModel: V by fragment.appViewModels {
        ViewModelCreator(
            clazz = clazz,
            navGraphId = navGraphId
        )
    }
}

private class PreviewStateful<V: ViewModel>: Stateful<V>()

fun <V: ViewModel> Stateful<V>.findViewModel(): V {
    return if (this is DefaultStateful<V>) {
        viewModel
    } else {
        throw IllegalStateException("ViewModel not found in Preview")
    }
}

@Composable
inline fun <reified V: ViewModel> createStatefulScope(): Stateful<V> {
    val isPreview = LocalInspectionMode.current
    return if (!isPreview) {
        val fragment = localFragment()
        remember {
            Stateful.create(
                fragment = fragment,
                clazz = V::class
            )
        }
    } else {
        remember {
            Stateful.preview()
        }
    }
}

@Composable
fun <V: ViewModel> Stateful<V>.LoaderEventConnector() {
    if (this is DefaultStateful) {
        val viewModel = findViewModel()
        if (viewModel is LoaderHandler) {
            val fragment = localFragment()
            fragment.connectLoadingEvent(viewModel)
        }
    }
}

@Composable
inline fun <reified V: ViewModel> Stateful(
    noinline content: @Composable Stateful<V>.() -> Unit
) {
    val scope = createStatefulScope<V>().apply {
        LoaderEventConnector()
    }
    StatefulProvider(stateful = scope) {
        UiEffectStoreProvider {
            GlobalUiEffectProvider.Register()
            scope.content()
            val uiEffectStore = LocalUiEffectStore.current
            uiEffectStore.modalUiEffectStore.forEach {
                it.value()
            }
        }
    }
}