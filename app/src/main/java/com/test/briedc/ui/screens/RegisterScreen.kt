package com.test.briedc.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.test.briedc.data.models.RegisterModel
import com.test.briedc.data.models.base.UIStateResponse
import com.test.briedc.router.Screens
import com.test.briedc.ui.components.InputTextWithIcon
import com.test.briedc.ui.components.InteractiveButton
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.GlobalUtils
import com.test.briedc.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(context: Context, navigationController: NavHostController) {

    // data field
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // click action
    var registerClick by remember { mutableStateOf(false) }
    var isErrorInput by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loginClick by remember { mutableStateOf(false) }

    // viewmodel init
    val authViewModel: AuthViewModel = viewModel()

    // register state
    val registerState = authViewModel.stateRegister.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, GlobalUtils.statusBarPadding, 0.dp, GlobalUtils.navigationBarPadding)
            .background(AppTheme.colors.background)
    ) {
        // Instancing element
        val (top_bg, cmp_register, bri_icon, intro_1) = createRefs()

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
                    bottom.linkTo(cmp_register.top, margin = 10.dp)
                },
            contentScale = ContentScale.Fit
        )

        // Register Component
        Column(
            modifier = Modifier
                .constrainAs(cmp_register) {
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
                text = "Bergabung Sekarang!",
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

            InputTextWithIcon(
                value = confirmPassword,
                placeHolder = "Konfirmasi password disini..",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                icon = Icons.Default.Lock,
                iconColor = AppTheme.colors.background,
                cursorColor = AppTheme.colors.surface,
                isPassword = true,
                isError = false
            ) { resultValue ->
                confirmPassword = resultValue
                Log.d("onInputText", "RegisterScreen confirm-password: $confirmPassword")
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

            if (registerClick) {
                if (username.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                    isErrorInput = true
                    errorMessage = "Data harus diisi semua!"
                    registerClick = false
                } else {
                    if (username.isEmpty()) {
                        isErrorInput = true
                        errorMessage = "Username harus diisi!"
                        registerClick = false
                    } else if (password.isEmpty()) {
                        isErrorInput = true
                        errorMessage = "Password harus diisi!"
                        registerClick = false
                    } else if (confirmPassword.isEmpty()) {
                        isErrorInput = true
                        errorMessage = "Harap konfirmasi password dahulu"
                        registerClick = false
                    } else if (password.isNotEmpty() && confirmPassword.isNotEmpty() && password != confirmPassword) {
                        isErrorInput = true
                        errorMessage = "Pastikan konfirmasi password sudah sesuai!"
                        registerClick = !registerClick
                    } else if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword) {
                        isErrorInput = false
                        Log.d("RegisterScreen", "onRegister: ${GlobalUtils.sha256Hex(password)}")
                        val body = JsonObject().apply {
                            addProperty("password", GlobalUtils.sha256Hex(password))
                            addProperty("username", username)
                        }
                        authViewModel.register(body)
                    }
                }
            }

            // register action
            when (val s = registerState.value) {
                is UIStateResponse.Loading -> {
                    if (registerClick && !isErrorInput) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 15.dp)
                        )
                    }
                }
                is UIStateResponse.Error -> {
                    registerClick = false
                    isErrorInput = true
                    errorMessage = s.message
                }
                is UIStateResponse.Success -> {
                    val registerResponse = s.response as RegisterModel
                    registerClick = false
                    isErrorInput = false
                    doRegister(context, registerResponse.message, navigationController)
                }
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
                isErrorInput = false
                registerClick = true
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Atau sudah punya akun?",
                color = AppTheme.colors.background,
                style = AppTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            // login action
            if (loginClick) {
                directLogin(navigationController)
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
                loginClick = !loginClick
            }
        }
    }
}


@Composable
fun doRegister(context: Context, message: String, navHostController: NavHostController) {
    LaunchedEffect(Unit)  {
        delay(500)
        navHostController.navigate(Screens.Login.route) {
            // clear all screen above or already screen active until seen route Splash
            popUpTo(Screens.Register.route) {
                // after clear all screen above until seen Splash route, clear route Splash also
                inclusive = true
            }
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun directLogin(navHostController: NavHostController) {
    LaunchedEffect(Unit)  {
        delay(500)
        navHostController.navigate(Screens.Login.route) {
            // clear all screen above or already screen active until seen route Splash
            popUpTo(Screens.Register.route) {
                // after clear all screen above until seen Splash route, clear route Splash also
                inclusive = true
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()
    AppTheme {
        RegisterScreen(context, navController)
    }
}