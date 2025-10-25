package xcom.niteshray.xapps.showverse.ui.theme

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

// Pure Black AMOLED Theme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1DB954), // Spotify Green
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF1A1A1A),
    onPrimaryContainer = Color(0xFF1DB954),
    secondary = Color(0xFF1DB954),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF282828),
    onSecondaryContainer = Color(0xFFB3B3B3),
    tertiary = Color(0xFF1DB954),
    background = Color(0xFF000000), // Pure Black
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF121212), // Slightly lighter than black
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF1E1E1E),
    onSurfaceVariant = Color(0xFFB3B3B3),
    error = Color(0xFFFF5252),
    onError = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFBB86FC),
    onPrimaryContainer = Color(0xFF21005E),
    secondary = Color(0xFF03DAC6),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF80E8DE),
    onSecondaryContainer = Color(0xFF002020),
    tertiary = Color(0xFFF50057),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFB00020),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun ShowVerseTheme(
    darkTheme: Boolean = true, // Always dark mode
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Always use dark color scheme
    val colorScheme = DarkColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color(0xFF000000).toArgb() // Pure black status bar
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}