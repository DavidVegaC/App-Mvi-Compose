package com.davega.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import com.davega.ui.R
import com.davega.ui.theme.ArchitectureAndroidTheme

abstract class BaseFragment(
    private val fullScreen: Boolean = false,
    private val animation: Int? = null,
    isCancelable: Boolean = true
): DialogFragment() {

    init {
        this.isCancelable = isCancelable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(fullScreen){
            setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        } else {
            setStyle(STYLE_NO_TITLE, R.style.Theme_AppDialog)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        animation?.let { animation ->
            dialog.window?.setWindowAnimations(animation)
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            if(fullScreen){
                val width = ViewGroup.LayoutParams.MATCH_PARENT
                val height = ViewGroup.LayoutParams.MATCH_PARENT
                dialog.window?.setLayout(width, height)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onInit()
        return ComposeView(requireContext()).apply {
            setContent {
                ArchitectureAndroidTheme {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Screen()
                    }
                }
            }
        }
    }

    open fun onInit() {}

    @Composable
    abstract fun Screen()

    protected fun DialogFragment.show(){
        if(!isAdded){
            this.show(this@BaseFragment.childFragmentManager, null)
        }
    }

}