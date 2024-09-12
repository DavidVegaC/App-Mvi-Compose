package com.davega.products.ui.main

import androidx.compose.runtime.Composable
import com.davega.products.ui.navigation.main.BottomNavigationBar
import com.davega.ui.base.BaseFragment

class MainScreen: BaseFragment() {

    @Composable
    override fun Screen(){
        BottomNavigationBar()
    }

}