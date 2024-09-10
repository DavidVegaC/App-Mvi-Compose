package com.davega.ui.compose.util

import androidx.activity.ComponentDialog
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Composable que facilita la implementación de la función "OnBackPressed" para un Fragment.
 *
 * @param block La función de bloqueo que se ejecutará cuando se presione el botón "Atrás".
 */
@Composable
fun OnBackPressed(
    block: () -> Unit
) {
    if (!LocalInspectionMode.current) {
        val fragment = localFragment()
        remember {
            val callback = object : OnBackPressedCallback(false) {
                override fun handleOnBackPressed() {
                    isEnabled = false
                    block()
                    isEnabled = true
                }
            }
            when(fragment) {
                is DialogFragment -> when(val dialog = fragment.dialog) {
                    is BottomSheetDialog -> {
                        dialog.onBackPressedDispatcher
                            .addCallback(fragment, callback)
                    }
                    is ComponentDialog -> {
                        dialog.onBackPressedDispatcher
                            .addCallback(fragment, callback)
                    }
                    else -> Unit
                }
                else -> {
                    fragment.requireActivity().onBackPressedDispatcher
                        .addCallback(fragment, callback)
                }
            }
            callback.apply {
                isEnabled = true
            }
        }
    }
}