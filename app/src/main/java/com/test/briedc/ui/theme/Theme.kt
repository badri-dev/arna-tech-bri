package com.test.briedc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    /* providing local app colors into MaterialTheme with CompositionLocalProvider
    * 'provides' here means giving generic value extended into localAppColor Provider
    * and we have extendColor as thats generic Value */
    CompositionLocalProvider(
        localAppColors provides extendedColor,
        localAppTypography provides extendedTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}

// AppTheme object will being global AppTheme which can be taking each field value inside AppTheme Compose
object AppTheme {
    val colors: AppColors
    @Composable
    get() = localAppColors.current
    /* 'current' here for declare state value of local app colors which saved at CompositionLocalProvider while Theme used */

    val typography: AppTypography
    @Composable
    get() = localAppTypography.current
    /* 'current' here for declare state value of local Typography which saved at CompositionLocalProvider while Theme used */
}