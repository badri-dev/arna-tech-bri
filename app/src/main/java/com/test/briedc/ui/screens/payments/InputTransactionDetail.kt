package com.test.briedc.ui.screens.payments

import android.content.Context
import android.net.Uri
import android.util.Log
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
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.test.briedc.R
import com.test.briedc.data.models.DetailTransaction
import com.test.briedc.ui.components.InputTextWithIcon
import com.test.briedc.ui.components.InteractiveButton
import com.test.briedc.ui.screens.home.directCardless
import com.test.briedc.ui.screens.home.directChip
import com.test.briedc.ui.screens.home.directManual
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.AppPreferencesManager
import com.test.briedc.utils.GlobalUtils

@Composable
fun InputTransactionDetail(isFrom: String?, context: Context, navHostController: NavHostController) {
    // data field
    var merchantId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var continueClick by remember { mutableStateOf(false) }
    var isErrorInput by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, GlobalUtils.statusBarPadding, 0.dp, GlobalUtils.navigationBarPadding)
            .background(Color.White)
    ) {
        // get cache merchant id
        merchantId = AppPreferencesManager.getString(context, GlobalUtils.MERCHANT_ID_KEY, merchantId)
        Log.d("TransactionDetail", "InputTransactionDetail from: $isFrom")
        // Instancing element
        val (top_bg, bri_icon, welcome_msg, cmp_form, create_by) = createRefs()

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
                    start.linkTo(parent.start, margin = 20.dp)
                }
                .height(60.dp),
            contentScale = ContentScale.Fit
        )

        // Wellcome text
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(welcome_msg) {
                    top.linkTo(bri_icon.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                }
        ) {
            Text(
                text = "Masukan Detail Transaksi",
                color = AppTheme.colors.surface,
                style = AppTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Silahkan masukan detail transaksi \npada form dibawah",
                color = AppTheme.colors.surface,
                style = AppTheme.typography.titleMedium
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(cmp_form) {
                    top.linkTo(welcome_msg.bottom, margin = 25.dp)
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
            Spacer(modifier = Modifier.height(15.dp))

            InputTextWithIcon(
                value = merchantId,
                placeHolder = "Masukan ID Toko disini..",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                icon = Icons.Default.Store,
                iconColor = AppTheme.colors.background,
                cursorColor = AppTheme.colors.surface,
                isPassword = false,
                isError = false
            ) { resultValue ->
                merchantId = resultValue
                Log.d("onInputText", "RegisterScreen Email: $merchantId")
            }

            Spacer(modifier = Modifier.height(15.dp))

            InputTextWithIcon(
                value = amount,
                placeHolder = "Masukan nominal disini..",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                icon = Icons.Default.Email,
                iconColor = AppTheme.colors.background,
                cursorColor = AppTheme.colors.surface,
                isPassword = false,
                isError = false
            ) { resultValue ->
                amount = resultValue
                Log.d("onInputText", "RegisterScreen Email: $amount")
            }

            Spacer(modifier = Modifier.height(15.dp))

            InputTextWithIcon(
                value = description,
                placeHolder = "Catatan pembayaran (Optional)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                icon = Icons.Default.Lock,
                iconColor = AppTheme.colors.background,
                cursorColor = AppTheme.colors.surface,
                isPassword = false,
                isError = false
            ) { resultValue ->
                description = resultValue
                Log.d("onInputText", "RegisterScreen password: $description")
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

            if (continueClick) {
                if (amount.isEmpty() || merchantId.isEmpty()) {
                    isErrorInput = true
                    errorMessage = "Nominal dan ID Toko harus di isi!"
                    continueClick = false
                } else {
                    isErrorInput = false
                    if (AppPreferencesManager.getString(context, GlobalUtils.MERCHANT_ID_KEY, "").isEmpty())
                        AppPreferencesManager.setString(context, GlobalUtils.MERCHANT_ID_KEY, merchantId)
                    val datas = DetailTransaction(
                        merchantId,
                        amount.toInt()
                    )
                    directPayment(datas, isFrom, navHostController)
                }
            }

            InteractiveButton(
                text = "Lanjutkan",
                startDefaultColor = AppTheme.colors.surface,
                endDefaultColor = AppTheme.colors.onSurface,
                startPressedColor = AppTheme.colors.onSurface,
                endPressedColor = AppTheme.colors.surface,
                textColorDefault = Color.White,
                textColorPressed = AppTheme.colors.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                isErrorInput = false
                continueClick = true
            }
        }

        Text(
            text = "By BRI",
            modifier = Modifier
                .constrainAs(create_by) {
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = AppTheme.typography.titleMedium,
            color = AppTheme.colors.surface
        )
    }
}

@Composable
fun directPayment(datas: DetailTransaction, from: String?, navHostController: NavHostController) {
    when (from) {
        "chip" -> {
            directChip(navHostController)
        }

        "tap" -> {
            val json = Uri.encode(Gson().toJson(datas))
            directCardless(json, navHostController)
        }

        "manual" -> {
            directManual(navHostController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputTransactionDetailPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        InputTransactionDetail(null, context, navHostController)
    }
}