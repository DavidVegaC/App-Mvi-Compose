package com.davega.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.davega.ui.R
import com.davega.ui.theme.ArchitectureAndroidTheme
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ScreenBottomSheetDialog(
    isCancelable: Boolean = true
): BottomSheetDialogFragment() {

    init {
        this.isCancelable = isCancelable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_AppBottomSheetDialog);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ArchitectureAndroidTheme {
                    Screen()
                }
            }
        }
    }

    @Composable
    abstract fun Screen()

}