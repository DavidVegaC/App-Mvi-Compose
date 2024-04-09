package com.davega.ui.base

import com.davega.ui.R

abstract class ScreenDialog(
    fullScreen: Boolean = false,
    animation: Int? = R.style.dialog_animation,
    isCancelable: Boolean = true
): BaseFragment(
    fullScreen = fullScreen,
    animation = animation,
    isCancelable = isCancelable
)