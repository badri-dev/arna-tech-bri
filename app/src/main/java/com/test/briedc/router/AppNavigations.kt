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

@Composable
fun AppNavigations(context: Context, navigationController: NavHostController) {
    NavHost(
        navController = navigationController,
        startDestination = Screens.Splash.route
    ) {
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