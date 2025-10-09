package com.test.briedc.router

/** Semua screen yang memerlukan navigate dalam pengaksesannya harus di deklarasikan disini
 * untuk digunakan pada navigasi router saat perpindahan screen */
sealed class Screens(val route: String) {
    object Splash: Screens("splash")
    object Home: Screens("home")
    object Register: Screens("register")
    object Login: Screens("Login")
}