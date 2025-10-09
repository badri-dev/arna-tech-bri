package com.test.briedc.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.JsonObject
import com.test.briedc.R
import com.test.briedc.data.models.LoginModel
import com.test.briedc.data.models.base.UIStateResponse
import com.test.briedc.router.Screens
import com.test.briedc.ui.components.InputTextWithIcon
import com.test.briedc.ui.components.InteractiveButton
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.AppPreferencesManager
import com.test.briedc.utils.GlobalUtils
import com.test.briedc.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(context: Context, navHostController: NavHostController) {

    // data field
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // click action
    var registerClick by remember { mutableStateOf(false) }
    var loginClick by remember { mutableStateOf(false) }
    var isErrorInput by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // viewmodel init
    val authViewModel: AuthViewModel = viewModel()

    // login state
    val loginState = authViewModel.stateLogin.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, GlobalUtils.statusBarPadding, 0.dp, GlobalUtils.navigationBarPadding)
            .background(AppTheme.colors.background)
    ) {
        // Instancing element
        val (top_bg, cmp_login, bri_icon, intro_1) = createRefs()

        // Top Background Image
        Image(
            painter = painterResource(R.drawable.bg_top_side),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(top_bg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Fit
        )

        // BRI Icons
        Image(
            painter = painterResource(R.drawable.ic_bri_light),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(bri_icon) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
                .height(50.dp),
            contentScale = ContentScale.Fit
        )

        // Intro Image
        Image(
            painter = painterResource(R.drawable.intro_1),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(intro_1) {
                    bottom.linkTo(cmp_login.top, margin = 10.dp)
                },
            contentScale = ContentScale.Fit
        )

        // Register Component
        Column(
            modifier = Modifier
                .constrainAs(cmp_login) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 26.dp,
                        topEnd = 26.dp,
                        0.dp,
                        0.dp
                    )
                )
                .background(Color.White)
                .padding(16.dp, 44.dp, 16.dp, 44.dp)
        ) {
            Text(
                text = "Masuk Sekarang!",
                color = AppTheme.colors.background,
                style = AppTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Untuk menerima transaksi yang lebih mudah, cepat dan aman.",
                color = AppTheme.colors.background,
                style = AppTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(28.dp))

            InputTextWithIcon(
                value = username,
                placeHolder = "Masukan email disini..",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                icon = Icons.Default.Email,
                iconColor = AppTheme.colors.background,
                cursorColor = AppTheme.colors.surface,
                isPassword = false,
                isError = false
            ) { resultValue ->
                username = resultValue
                Log.d("onInputText", "RegisterScreen Email: $username")
            }

            Spacer(modifier = Modifier.height(15.dp))

            InputTextWithIcon(
                value = password,
                placeHolder = "Masukan password disini..",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                icon = Icons.Default.Lock,
                iconColor = AppTheme.colors.background,
                cursorColor = AppTheme.colors.surface,
                isPassword = true,
                isError = false
            ) { resultValue ->
                password = resultValue
                Log.d("onInputText", "RegisterScreen password: $password")
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Error visibility
            AnimatedVisibility(
                visible = isErrorInput,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = errorMessage,
                    style = AppTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
            }

            if (loginClick) {
                if (username.isEmpty() || password.isEmpty()) {
                    isErrorInput = true
                    errorMessage = "Username dan password harus di isi!"
                    loginClick = false
                } else {
                    isErrorInput = false
                    Log.d("LoginScreen", "onLogin: ${GlobalUtils.sha256Hex(password)}")
                    val body = JsonObject().apply {
                        addProperty("password", GlobalUtils.sha256Hex(password))
                        addProperty("username", username)
                    }
                    authViewModel.login(body)
                }
            }

            // login action
            when (val s = loginState.value) {
                is UIStateResponse.Loading -> {
                    if (loginClick && !isErrorInput) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 15.dp)
                        )
                    }
                }
                is UIStateResponse.Error -> {
                    loginClick = false
                    isErrorInput = true
                    errorMessage = s.message
                }
                is UIStateResponse.Success -> {
                    val userToken = s.response as LoginModel
                    loginClick = false
                    isErrorInput = false
                    AppPreferencesManager.setString(context, GlobalUtils.TOKEN_KEY, userToken.token)
                    doLogin(navHostController)
                }
                else -> Unit
            }

            InteractiveButton(
                text = "Masuk",
                startDefaultColor = AppTheme.colors.surface,
                endDefaultColor = AppTheme.colors.onSurface,
                startPressedColor = AppTheme.colors.onSurface,
                endPressedColor = AppTheme.colors.surface,
                textColorDefault = Color.White,
                textColorPressed = AppTheme.colors.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                isErrorInput = false
                loginClick = true
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Atau belum punya akun?",
                color = AppTheme.colors.background,
                style = AppTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            // register action
            if (registerClick) {
                directRegister(navHostController)
            }

            InteractiveButton(
                text = "Daftar",
                startDefaultColor = AppTheme.colors.surface,
                endDefaultColor = AppTheme.colors.onSurface,
                startPressedColor = AppTheme.colors.onSurface,
                endPressedColor = AppTheme.colors.surface,
                textColorDefault = Color.White,
                textColorPressed = AppTheme.colors.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                registerClick = !registerClick
            }
        }
    }
}

@Composable
fun directRegister(navHostController: NavHostController) {
    LaunchedEffect(Unit)  {
        delay(500)
        navHostController.navigate(Screens.Register.route) {
            // clear all screen above or already screen active until seen route Splash
            popUpTo(Screens.Login.route) {
                // after clear all screen above until seen Splash route, clear route Splash also
                inclusive = true
            }
        }
    }
}

@Composable
fun doLogin(navHostController: NavHostController) {
    LaunchedEffect(Unit)  {
        delay(500)
        navHostController.navigate(Screens.Home.route) {
            // clear all screen above or already screen active until seen route Splash
            popUpTo(Screens.Login.route) {
                // after clear all screen above until seen Splash route, clear route Splash also
                inclusive = true
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current
    val navController = rememberNavController()
    AppTheme {
        LoginScreen(context, navController)
    }
}