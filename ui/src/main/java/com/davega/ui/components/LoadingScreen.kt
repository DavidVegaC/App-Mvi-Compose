package com.davega.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.davega.ui.R
import com.davega.ui.base.BaseFragment

class LoadingScreen: BaseFragment(
    fullScreen = true,
    isCancelable = false,
    animation = R.style.loading_dialog_animation
) {

    @Composable
    override fun Screen(){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AppLoading(
                label = stringResource(
                    id = R.string.loading
                )
            )
        }
    }

}