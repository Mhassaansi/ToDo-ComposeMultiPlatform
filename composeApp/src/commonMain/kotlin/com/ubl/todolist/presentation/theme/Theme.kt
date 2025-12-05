package com.ubl.todolist.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Color

@Composable
fun TodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = LightColors
       // if (darkTheme) DarkColors else

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}

private val LightColors = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    error = LightError,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface
)

// 🌙 Dark Color Scheme
//private val DarkColors = darkColorScheme(
//    primary = DarkPrimary,
//    secondary = DarkSecondary,
//    error = DarkError,
//    background = DarkBackground,
//    surface = DarkSurface,
//    onPrimary = DarkOnPrimary,
//    onSecondary = DarkOnSecondary,
//    onBackground = DarkOnBackground,
//    onSurface = DarkOnSurface
//)

