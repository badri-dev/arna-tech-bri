package com.test.briedc.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test.briedc.R

private val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_regular_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.roboto_regular_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.roboto_regular_bold, FontWeight.Bold, FontStyle.Normal)
)

@Immutable
data class AppTypography(
    val headLine: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val label: TextStyle,
    val labelMedium: TextStyle,
    val labelBold: TextStyle
)

// this will being provider local typography with CompositionLocalProvider for our theme
val localAppTypography = staticCompositionLocalOf {
    AppTypography(
        headLine = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        body = TextStyle.Default,
        bodySmall = TextStyle.Default,
        label = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelBold = TextStyle.Default
    )
}

// this will being immutable global variable typography for our theme
val extendedTypography = AppTypography(
    headLine = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    body = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    label = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Light
    ),
    labelMedium = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold
    ),
    labelBold = TextStyle(
        fontFamily = RobotoFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )
)