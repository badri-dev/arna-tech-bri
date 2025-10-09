package com.test.briedc.ui.screens.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.test.briedc.R
import com.test.briedc.ui.components.PaymentButton
import com.test.briedc.ui.theme.AppTheme

@Composable
fun SaleScreen(context: Context, navHostController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Instancing element
        val (top_bg, bri_icon, welcome_msg, feature_box, feature_items) = createRefs()

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
                text = "Hi Sobat BRI!",
                color = AppTheme.colors.surface,
                style = AppTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Sudah siap?\nMari mulai perjalanan yang Mudah, \nCepat dan Aman dengan kami.",
                color = AppTheme.colors.surface,
                style = AppTheme.typography.titleMedium
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(feature_box) {
                    top.linkTo(welcome_msg.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                    width = Dimension.fillToConstraints
                }
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(18.dp),
                    clip = false // kalau true, konten di dalam ikut di-clip
                )
                .clip(
                    RoundedCornerShape(
                        18.dp
                    )
                )
                .background(Color.White)
                .padding(8.dp)
        ) {
            var message by remember { mutableStateOf("") }
            var showManualEntry by remember { mutableStateOf(false) }
            var cardNumber by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .constrainAs(feature_items) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Metode Pembayaran: ",
                    color = AppTheme.colors.surface,
                    style = AppTheme.typography.titleMedium
                )

                // 🟢 Insert (Chip)
                PaymentButton(
                    icon = Icons.Default.CreditCard,
                    text = "Insert (Chip)",
                    color = Color(0xFF4CAF50),
                    onClick = {
                        showManualEntry = false
                        message = "Reading Chip…"
                    }
                )

                // 🔵 Tap (Contactless)
                PaymentButton(
                    icon = Icons.Default.Nfc,
                    text = "Tap (Contactless)",
                    color = Color(0xFF2196F3),
                    onClick = {
                        showManualEntry = false
                        message = "Reading NFC Card…"
                    }
                )

                // 🟠 Manual Entry
                PaymentButton(
                    icon = Icons.Default.Keyboard,
                    text = "Manual Entry",
                    color = Color(0xFFFF9800),
                    onClick = {
                        showManualEntry = true
                        message = ""
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dynamic content
                when {
                    message.isNotEmpty() -> {
                        Text(
                            text = message,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    showManualEntry -> {
                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = { cardNumber = it },
                            label = { Text("Enter Card Number") },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SaleScreenPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        SaleScreen(context, navHostController)
    }
}