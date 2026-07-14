package com.example.forum.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = Gray80,
    tertiary = BackgroundDarkColor,

    background = BackgroundDarkColor,

    primaryContainer = Dark,
    surface = Purple80,
    surfaceVariant = Red,

    onPrimary = White,
    onSecondary = White,
    onTertiary = Purple40,

    onBackground = White,

    onPrimaryContainer = White,
    onSurface = White,
    onSurfaceVariant = Gray90,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    secondary = Gray40,
    tertiary = White,

    background = BackgroundLightColor,

    primaryContainer = White,
    surface = Green,
    surfaceVariant = Red,

    onPrimary = White,
    onSecondary = White,
    onTertiary = Gray,

    onBackground = Gray40,

    onPrimaryContainer = Black,
    onSurface = White,
    onSurfaceVariant = Gray,
)

@Composable
fun ForumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}