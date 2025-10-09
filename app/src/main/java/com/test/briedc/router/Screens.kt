package com.test.briedc.router

sealed class Screens(val route: String) {
    object Splash: Screens("splash")
    object Home: Screens("home")
    object Register: Screens("register")
    object Login: Screens("Login")
}