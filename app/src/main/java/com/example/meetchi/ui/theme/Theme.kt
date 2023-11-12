package com.example.meetchi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


enum class AppTheme {
    THEME_STANDARD,
    THEME_LIGHT,
    THEME_DARK
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF0E0E0E)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFFFF)
)

private val StandardColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFED4E8)
)
@Composable
fun MeetchiTheme(
    appTheme: AppTheme = AppTheme.THEME_STANDARD, // Thème par défaut
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
)
{
    val colorScheme = when (appTheme)
    {
        AppTheme.THEME_STANDARD -> StandardColorScheme
        AppTheme.THEME_LIGHT -> LightColorScheme
        AppTheme.THEME_DARK -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}