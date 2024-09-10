package com.davega.ui.compose.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.davega.ui.compose.util.FragmentProvider
import com.davega.ui.core.fragment.AppBaseFragment
import com.davega.ui.theme.ArchitectureAndroidTheme

/**
 * Clase base para fragments con compose.
 */
abstract class AppComposeFragment: AppBaseFragment() {

    open fun onInit() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onInit()
        return ComposeView(requireContext()).apply {
            setContent {
                FragmentProvider {
                    ArchitectureAndroidTheme {
                        Screen()
                    }
                }
            }
        }
    }

    /**
     * Composable abstracto que representa la pantalla del fragment.
     */
    @Composable
    abstract fun Screen()

    /**
     * Composable abstracto que se utiliza para la vista previa de la pantalla en el IDE.
     */
    @Composable
    abstract fun DefaultPreview()
}