package com.pregnancy.vitals.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Single color scheme for both light and dark mode
private val AppColorScheme = lightColorScheme(
    primary = MediumPurple,
    secondary = DarkPurple,
    tertiary = LightPurple,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    secondaryContainer = LightPurple,
    onSecondaryContainer = DarkPurple,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkPurple,
    onSurface = DarkPurple
)

@Composable
fun PregnancyVitalsTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,  // Disabled to use our custom purple theme
    content: @Composable () -> Unit
) {
    // Always use the same color scheme regardless of dark mode
    val colorScheme = AppColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}