package com.test.briedc.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.test.briedc.R
import com.test.briedc.router.Screens
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.GlobalUtils
import com.test.briedc.utils.GlobalUtils.ValidateLogin
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(context: Context, navigationController: NavHostController) {

    // Load animation from raw asset
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1 // just 1 time playing animation
    )

    ValidateLogin(context) { hasLoggedIn ->
        if (hasLoggedIn) {
            LaunchedEffect(Unit)  {
                delay(2000)
                navigationController.navigate(Screens.Home.route) {
                    // clear all screen above or already screen active until seen route Splash
                    popUpTo(Screens.Splash.route) {
                        // after clear all screen above until seen Splash route, clear route Splash also
                        inclusive = true
                    }
                }
            }
        } else {
            LaunchedEffect(Unit)  {
                delay(2000)
                navigationController.navigate(Screens.Login.route) {
                    // clear all screen above or already screen active until seen route Splash
                    popUpTo(Screens.Splash.route) {
                        // after clear all screen above until seen Splash route, clear route Splash also
                        inclusive = true
                    }
                }
            }
        }
    }

    // UI Splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, GlobalUtils.statusBarPadding, 0.dp, GlobalUtils.navigationBarPadding)
    ) {
        Log.d("SplashScreen", "SplashScreen: ${GlobalUtils.navigationBarPadding}")
        // Background Image
        Image(
            painter = painterResource(R.drawable.bg_edc),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Animation Lottie above background Image
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        Text(
            text = "By BRI",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 26.dp),
            style = AppTheme.typography.titleMedium,
            color = AppTheme.colors.surface
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()
    AppTheme {
        SplashScreen(context, navController)
    }
}