package com.ubl.todolist.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color



// 🌞 Light Mode Colors
val LightPrimary = Color(0xFF4CAF50)        // Green 500
val LightPrimaryVariant = Color(0xFF388E3C) // Darker Green
val LightSecondary = Color(0xFFFFC107)      // Amber
val LightError = Color(0xFFF44336)          // Red

val LightBackground = Color(0xFFFAFAFA)     // Light grey-white
val LightSurface = Color(0xFFFFFFFF)        // Pure white
val LightOnPrimary = Color(0xFFFFFFFF)      // White on Green
val LightOnSecondary = Color(0xFF212121)    // Black text on amber
val LightOnBackground = Color(0xFF212121)   // Dark text
val LightOnSurface = Color(0xFF424242)      // Grey text

val LightHeaderGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF4CAF50), Color(0xFF2196F3)) // Green → Blue
)
val LightTaskDetailsGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFEEEEEE), Color(0xFFFFFFFF)) // Gray → White
)

// 🌙 Dark Mode Colors
val DarkPrimary = Color(0xFF4CAF50)         // Green 500
val DarkPrimaryVariant = Color(0xFF388E3C)  // Darker Green
val DarkSecondary = Color(0xFFFFC107)       // Amber
val DarkError = Color(0xFFF44336)           // Red

val DarkBackground = Color(0xFF121212)      // Deep dark background
val DarkSurface = Color(0xFF1E1E1E)         // Dark grey surface
val DarkOnPrimary = Color(0xFFFFFFFF)       // White text/icons on green
val DarkOnSecondary = Color(0xFF212121)     // Dark text/icons on amber
val DarkOnBackground = Color(0xFFE0E0E0)    // Light grey text
val DarkOnSurface = Color(0xFFBDBDBD)       // Softer grey text

// Gradients for Dark Theme
val DarkHeaderGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF388E3C), Color(0xFF1565C0)) // Darker Green → Blue
)
val DarkTaskDetailsGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF1E1E1E), Color(0xFF121212)) // Subtle gray blend
)
