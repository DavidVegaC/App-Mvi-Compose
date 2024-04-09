package com.davega.ui.components

import android.content.res.Resources
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.*

@Composable
fun AppAnimation(
    modifier: Modifier = Modifier,
    @androidx.annotation.RawRes resId: Int,
    iterations: Int = LottieConstants.IterateForever,
    contentScale: ContentScale = ContentScale.Fit,
    onFinish: (() -> Unit)? = null,
    onProgress: ((progress: Float) -> Unit)? = null,
    speed: Float = 1f
){
    val density = remember {
        Resources.getSystem().displayMetrics.density
    }
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId)
    )
    val ratio = composition?.bounds?.let {
        val result = ((it.width() / density) / (it.height() / density))
        if(result > 0f) result else 1f
    } ?: 1f
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        speed = speed
    )
    onFinish?.let {
        LaunchedEffect(progress){
            if(progress == 1f){
                onFinish()
            }
        }
    }
    onProgress?.let {
        LaunchedEffect(progress){
            onProgress(progress)
        }
    }
    LottieAnimation(
        composition = composition,
        progress = progress,
        contentScale = contentScale,
        modifier = Modifier
            .aspectRatio(ratio)
            .then(modifier)
    )
}