package com.test.briedc.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val BlueBRI = Color(0xFF0786C1)
val LightButton = Color(0xFF7ECDF4)
val DarkButton = Color(0xFF2693CA)
val Dark = Color(0xFF082533)

@Immutable
data class AppColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val secondarySurface: Color,
    val onSecondarySurface: Color,
    val regularSurface: Color,
    val onRegularSurface: Color,
    val actionSurface: Color,
    val onActionSurface: Color,
    val highlightSurface: Color,
    val onHighlightSurface: Color,
)

// this will being provider local color with CompositionLocalProvider for our theme
val localAppColors = staticCompositionLocalOf {
    AppColors(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        secondarySurface = Color.Unspecified,
        onSecondarySurface = Color.Unspecified,
        regularSurface = Color.Unspecified,
        onRegularSurface = Color.Unspecified,
        actionSurface = Color.Unspecified,
        onActionSurface = Color.Unspecified,
        highlightSurface = Color.Unspecified,
        onHighlightSurface = Color.Unspecified
    )
}

// this will being immutable global variable AppColors for our theme
val extendedColor = AppColors(
    background = Color.White,
    onBackground = Dark,
    surface = LightButton,
    onSurface = DarkButton,
    secondarySurface = LightButton,
    onSecondarySurface = BlueBRI,
    regularSurface = LightButton,
    onRegularSurface = DarkButton,
    actionSurface = LightButton,
    onActionSurface = BlueBRI,
    highlightSurface = LightButton,
    onHighlightSurface = DarkButton
)