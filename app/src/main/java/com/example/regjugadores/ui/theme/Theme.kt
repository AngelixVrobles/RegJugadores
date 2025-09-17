package com.example.regjugadores.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// ✅ Paleta modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = TealSecondaryDark,
    onSecondary = OnSecondaryDark,
    tertiary = OrangeAccentDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark
)

// ✅ Paleta modo claro
private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = OnPrimaryLight,
    secondary = TealSecondary,
    onSecondary = OnSecondaryLight,
    tertiary = OrangeAccent,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight
)

@Composable
fun RegJugadoresTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // 🔒 fijo en nuestra paleta personalizada
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
