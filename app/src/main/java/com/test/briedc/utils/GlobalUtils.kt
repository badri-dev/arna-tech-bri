package com.test.briedc.utils

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.security.MessageDigest
import java.text.DecimalFormat

object GlobalUtils {
    const val TOKEN_KEY = "user_token"
    const val MERCHANT_ID_KEY = "merchant_id"
    const val PREFERENCE_KEY = ""

    // padding by window size status bar
    val statusBarPadding: Dp
        @Composable
        get() = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    // padding by window size navigation bar
    val density: Density
        @Composable
        get() = LocalDensity.current
    val navigationBarHeight : Dp
        @Composable
        get() = with(density) { WindowInsets.navigationBars.getBottom(this).toDp() }

    /**
     * Gesture mode biasanya < 30.dp
     * Mulai dari Android 10 (API 29), Google memperkenalkan gesture navigation system UI
     * Tapi Android masih menganggap area bawah gesture navigation sebagai navigation bar, meskipun secara visual sudah transparan.
     */
    val isGestureMode: Boolean
        @Composable
        get() = this.navigationBarHeight < 30.dp
    val navigationBarPadding : Dp
        @Composable
        get() = if (isGestureMode) 0.dp else navigationBarHeight

    @Composable
    fun ValidateLogin(context: Context, hasLoggedIn : @Composable (Boolean) -> Unit) {
        val token = AppPreferencesManager.getString(context, TOKEN_KEY)
        if (token.isEmpty())
            hasLoggedIn(false)
        else
            hasLoggedIn(true)
    }

    fun sha256Hex(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) } // hex
    }

    fun idrFormat(value: Int): String {
        val formatter = DecimalFormat("#,###")
        val formattedValue = if (value >= 0) formatter.format(value) else formatter.format(value * -1)
        return if (value >= 0)
            "Rp${formattedValue.replace(",", ".")},-"
        else
            "-Rp${formattedValue.replace(",", ".")},-"
    }

    @Composable
    fun CountdownTimer(
        totalTime: Int = 10, // detik
        onFinish: () -> Unit = {}
    ) {
        var timeLeft by remember { mutableStateOf(totalTime) }

        LaunchedEffect(timeLeft) {
            if (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            } else {
                onFinish()
            }
        }

        Text(
            text = "Sisa waktu untuk pembayaran: $timeLeft",
            style = MaterialTheme.typography.labelMedium
        )
    }
}