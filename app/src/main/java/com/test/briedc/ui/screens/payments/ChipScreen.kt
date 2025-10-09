package com.test.briedc.ui.screens.payments

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.gson.JsonObject
import com.test.briedc.R
import com.test.briedc.data.models.DetailTransaction
import com.test.briedc.data.models.SaleModel
import com.test.briedc.data.models.base.UIStateResponse
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.AppPreferencesManager
import com.test.briedc.utils.GlobalUtils
import com.test.briedc.viewmodels.TransactionsViewModel

@Composable
fun ChipScreen(datas: DetailTransaction?, context: Context, navHostController: NavHostController) {

    val transactionVM: TransactionsViewModel = viewModel()
    val paymentState = transactionVM.stateSale.collectAsState()

    var requestSended by remember { mutableStateOf(false) }
    var countDownFinish by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, GlobalUtils.statusBarPadding, 0.dp, GlobalUtils.navigationBarPadding)
            .background(AppTheme.colors.background)
    ) {
        // Instancing element
        val (top_bg, anim_label, cmp_tap, bri_icon, intro_1, nfc_icon) = createRefs()

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
                    end.linkTo(parent.end, margin = 10.dp)
                }
                .height(50.dp),
            contentScale = ContentScale.Fit
        )

        // Intro Image
        Image(
            painter = painterResource(R.drawable.ic_illustration_tap),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(intro_1) {
                    bottom.linkTo(cmp_tap.top, margin = 10.dp)
                    top.linkTo(bri_icon.bottom)
                },
            contentScale = ContentScale.Fit
        )


        // visibility anim state
        var visible by remember { mutableStateOf(true) }

        // toggle effect
        LaunchedEffect(Unit) {
            while (true) {
                visible = !visible
                kotlinx.coroutines.delay(500)
            }
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .constrainAs(anim_label) {
                    bottom.linkTo(cmp_tap.top, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = "Membaca Kartu.....",
                style = AppTheme.typography.titleMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        // Chip Component
        Column(
            modifier = Modifier
                .constrainAs(cmp_tap) {
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
                .padding(16.dp, 44.dp, 16.dp, 44.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // Nama & nomor kartu
            Text(
                text = "•••••• | •••••• •••••• •••••• ••••••",
                style = AppTheme.typography.titleMedium,
                color = Color.Black.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Baris saldo (IDR)
            Row(
                modifier = Modifier
                    .background(
                        Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Logo negara
                Box (
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.Red, CircleShape)
                )

                Text(
                    text = "IDR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = GlobalUtils.idrFormat(datas?.amount ?: 0),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(20.dp))

            // Waiting text + timer
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlobalUtils.CountdownTimer(
                    totalTime = 5
                ) {
                    countDownFinish = true
                    Log.d("CountDown", "CardlessScreen: $countDownFinish")
                }
            }

            LaunchedEffect(countDownFinish) {
                if (countDownFinish) {
                    val body = JsonObject().apply {
                        addProperty("amount", datas?.amount)
                        addProperty("merchant_id", datas?.merchantId)
                        addProperty("card_number", "0290 3445 9681 112")
                    }
                    if (!requestSended)
                        transactionVM.postSale("Bearer ${AppPreferencesManager.getString(context, GlobalUtils.TOKEN_KEY, "")}", body)
                }
            }

            when (val s = paymentState.value) {
                is UIStateResponse.Loading -> {
                    if (countDownFinish) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 15.dp)
                        )
                    }
                }
                is UIStateResponse.Error -> {
                    transactionVM.clearStateSale()
                    Toast.makeText(context, s.message, Toast.LENGTH_SHORT).show()
                    requestSended = true
                }
                is UIStateResponse.Success -> {
                    transactionVM.clearStateSale()
                    Toast.makeText(context, (s.response as SaleModel).status, Toast.LENGTH_SHORT).show()
                    navHostController.popBackStack()
                    requestSended = true
                }
                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipScreenPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        ChipScreen(null, context, navHostController)
    }
}