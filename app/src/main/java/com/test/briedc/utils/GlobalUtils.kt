package com.test.briedc.utils

import android.content.Context
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.security.MessageDigest

object GlobalUtils {
    const val TOKEN_KEY = "user_token"
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
}