package com.davega.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = AppColors.primary,
    primaryContainer = Purple700,
    secondary = AppColors.secondary,
    background = AppColors.background,
    onSecondary = AppColors.onSecondary,
    onPrimary = AppColors.onPrimary,
    error = AppColors.error
)

private val LightColorPalette = lightColorScheme(
    primary = AppColors.primary,
    primaryContainer = Purple700,
    secondary = AppColors.secondary,
    background = AppColors.background,
    onSecondary = AppColors.onSecondary,
    onPrimary = AppColors.onPrimary,
    error = AppColors.error
)

@Composable
fun ArchitectureAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}