package com.test.briedc.router

import com.test.briedc.data.models.DetailTransaction
import java.io.Serializable

/** Semua screen yang memerlukan navigate dalam pengaksesannya harus di deklarasikan disini
 * untuk digunakan pada navigasi router saat perpindahan screen */
sealed class Screens(val route: String) {
    // Main route
    object Splash: Screens("splash")
    object Home: Screens("home")
    object Register: Screens("register")
    object Login: Screens("Login")
    // Payment route
    object Cardless: Screens("cardless/{datas}") {
        fun routeExtras(datas: String) = "cardless/$datas"
    }
    object Chip: Screens("chip")
    object Manual: Screens("manual")
    object TransactionDetail: Screens("detail/{from}") {
        fun routeExtras(from: String) = "detail/$from"
    }
}