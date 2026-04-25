package com.streamflix.iptv.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// StreamFlix Color Palette - Modern Android TV styling
val StreamFlixColors = darkColorScheme(
    primary = Color(0xFF00E5FF),      // Cyan accent for modern look
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF00B8CC),
    onPrimaryContainer = Color.White,
    
    secondary = Color(0xFFFF4081),    // Pink for highlights
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFC51162),
    onSecondaryContainer = Color.White,
    
    tertiary = Color(0xFF7C4DFF),     // Purple for special elements
    onTertiary = Color.White,
    
    background = Color(0xFF0A0E14),   // Deep blue-black background
    onBackground = Color(0xFFF5F7FA),
    
    surface = Color(0xFF151A23),      // Card/panel color
    onSurface = Color(0xFFF5F7FA),
    surfaceVariant = Color(0xFF1E2530),
    onSurfaceVariant = Color(0xFFB0B8C4),
    
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun StreamFlixTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StreamFlixColors,
        content = content
    )
}
