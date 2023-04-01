package com.example.multi_game.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = LightYellow1,
    primaryVariant = DarkPurple1,
    secondary = Teal200,
    background = DarkPurple2,
    surface = LightPurple1,
    onPrimary = DarkPurple1,
//    onSecondary = Color.Black,
    onBackground = LightYellow1,
    onSurface = LightYellow1,

    )

private val LightColorPalette = lightColors(
    primary = LightYellow2,
    primaryVariant = LightPurple2,
    secondary = Teal200,
    background = LightPink1,
    surface = DarkPurple1,
    onPrimary = LightPink1,
//    onSecondary = Color.Black,
    onBackground = LightYellow2,
    onSurface = LightPink1,

)

@Composable
fun MultiGameTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}