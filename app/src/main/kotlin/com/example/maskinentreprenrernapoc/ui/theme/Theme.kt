@file:Suppress("DEPRECATION")

package com.example.maskinentreprenrernapoc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark Theme Configuration
 * Defines color mappings for dark mode components.
 */
private val DarkColorScheme = darkColorScheme(
    primary = White80,
    secondary = White80,
    tertiary = White80,
    background = GreyBlack,
    surface = GreyBlack,
    onPrimary = GreyBlack,
    onSecondary = GreyBlack,
    onTertiary = GreyBlack,
    onBackground = White80,
    onSurface = White80,
    secondaryContainer = Color(0xFFE4F152), // Highlight color for containers
    onSecondaryContainer = Color(0xFF002269),
)

/**
 * Light Theme Configuration
 * Defines color mappings for light mode components.
 */
private val LightColorScheme = lightColorScheme(
    primary = White80,
    secondary = White80,
    tertiary = White80,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = GreyBlack,
    onSurface = GreyBlack,
    secondaryContainer = Color(0xFFE4F152), // Highlight color for containers
    onSecondaryContainer = Color(0xFF002269),
)

/**
 * Application Theme Wrapper
 * Applies the custom color scheme and handles system UI (status bar) styling.
 */
@Composable
fun NonrepresentationalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color support for Android 12+ (currently disabled)
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Select the appropriate color scheme based on user settings
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Sync the status bar color with the theme's primary color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Provide the theme to the rest of the application
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
