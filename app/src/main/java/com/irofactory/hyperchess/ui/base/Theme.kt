package com.irofactory.hyperchess.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = p04,
    primaryVariant = p00,
    secondary = c02
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = x02,
    primaryVariant = x04,
    secondary = r03,
    background = x05,
    surface = x05,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = x02,
)

@Composable
fun hyperTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
