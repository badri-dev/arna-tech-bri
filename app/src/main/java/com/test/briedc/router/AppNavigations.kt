package com.test.briedc.router

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.test.briedc.data.models.DetailTransaction
import com.test.briedc.ui.screens.HomeScreen
import com.test.briedc.ui.screens.LoginScreen
import com.test.briedc.ui.screens.RegisterScreen
import com.test.briedc.ui.screens.SplashScreen
import com.test.briedc.ui.screens.payments.CardlessScreen
import com.test.briedc.ui.screens.payments.ChipScreen
import com.test.briedc.ui.screens.payments.InputTransactionDetail
import com.test.briedc.ui.screens.payments.ManualScreen

/** NavHost disini berperan sebagai router screen untuk memudahkan perpindahan screen di satu activity */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigations(context: Context, navigationController: NavHostController) {
    NavHost(
        navController = navigationController,
        startDestination = Screens.Splash.route // screen pertama yang di jalankan
    ) {
        /** Screen yang sudah di tambahkan di Screens perlu kita inisialisasikan route dan screen tujuannya disini*/
        // Main route screen
        composable(Screens.Splash.route) {
            SplashScreen(context, navigationController)
        }
        composable(Screens.Home.route) {
            HomeScreen(context, navigationController)
        }
        composable(Screens.Login.route) {
            LoginScreen(context, navigationController)
        }
        composable(Screens.Register.route) {
            RegisterScreen(context, navigationController)
        }

        // Payment route screen
        composable(
            Screens.Cardless.route,
            arguments = listOf(
                navArgument("datas") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val datas = backStackEntry.arguments?.getString("datas")
            val data = Gson().fromJson(datas, DetailTransaction::class.java)
            CardlessScreen(data, context, navigationController)
        }
        composable(Screens.Chip.route) {
            ChipScreen(context, navigationController)
        }
        composable(Screens.Manual.route) {
            ManualScreen(context, navigationController)
        }
        composable(
            Screens.TransactionDetail.route,
            arguments = listOf(
            navArgument("from") { type = NavType.StringType }
        )
        ) { backStackEntry ->
            val isFrom = backStackEntry.arguments?.getString("from")
            InputTransactionDetail(isFrom, context, navigationController)
        }
    }
}