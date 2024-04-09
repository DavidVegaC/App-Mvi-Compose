package com.davega.products.ui.main

import androidx.compose.runtime.Composable
import com.davega.products.di.loadModules
import com.davega.products.ui.navigation.main.BottomNavigationBar
import com.davega.ui.base.BaseFragment

class MainScreen: BaseFragment() {

    override fun onInit() {
        loadModules()
    }

    @Composable
    override fun Screen(){
        BottomNavigationBar()
    }

}