package com.rocketseat.RRM.tabelanutricional.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    onPrimary = TextCta,
    onSecondary = TextSecondary,
    onBackground = TextPrimary,
    onSurface = SurfaceElement,
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkTextCta,
    onSecondary = DarkTextSecondary,
    onBackground = DarkTextPrimary,
    onSurface = DarkSurfaceElement,
)

@Composable
fun TabelaNutricionalTheme(
    sizing: Sizing = Sizing(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val sizing = if (darkTheme) Sizing(sm = 56.dp) else sizing
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
        content = {
            CompositionLocalProvider(
                LocalSizing provides(sizing)
            ) {
                content()
            }
        }
    )
}

object TabelaNutricionalTheme {
    val sizing: Sizing
        @Composable
        get() = LocalSizing.current
}
