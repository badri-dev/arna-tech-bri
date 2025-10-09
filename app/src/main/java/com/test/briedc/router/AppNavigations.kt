package com.test.briedc.router

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test.briedc.ui.screens.HomeScreen
import com.test.briedc.ui.screens.LoginScreen
import com.test.briedc.ui.screens.RegisterScreen
import com.test.briedc.ui.screens.SplashScreen

/** NavHost disini berperan sebagai router screen untuk memudahkan perpindahan screen di satu activity */
@Composable
fun AppNavigations(context: Context, navigationController: NavHostController) {
    NavHost(
        navController = navigationController,
        startDestination = Screens.Splash.route // screen pertama yang di jalankan
    ) {
        /** Screen yang sudah di tambahkan di Screens perlu kita inisialisasikan route dan screen tujuannya disini*/
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
    }
}