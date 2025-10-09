package com.test.briedc.ui.screens.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.test.briedc.ui.theme.AppTheme

@Composable
fun SettlementScreen(context: Context, navHostController: NavHostController) {

}

@Preview(showBackground = true)
@Composable
fun SettlementScreenPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        SettlementScreen(context, navHostController)
    }
}