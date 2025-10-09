package com.test.briedc.ui.screens.payments

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.test.briedc.ui.theme.AppTheme

@Composable
fun ManualScreen(context: Context, navHostController: NavHostController) {

}

@Preview(showBackground = true)
@Composable
fun ManualScreenPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        ManualScreen(context, navHostController)
    }
}