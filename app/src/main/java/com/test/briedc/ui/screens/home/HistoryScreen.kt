package com.test.briedc.ui.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.gson.JsonObject
import com.test.briedc.data.models.HistoryModel
import com.test.briedc.data.models.base.UIStateResponse
import com.test.briedc.ui.components.HistoryItems
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.AppPreferencesManager
import com.test.briedc.utils.GlobalUtils
import com.test.briedc.viewmodels.TransactionsViewModel

@Composable
fun HistoryScreen(context: Context, navHostController: NavHostController) {

    val transactionVM: TransactionsViewModel = viewModel()
    val historyState = transactionVM.stateTransactionHistory.collectAsState()
    val settlementState = transactionVM.stateSettlement.collectAsState()

    var approvedCount by remember { mutableStateOf(0) }
    var settledCount by remember { mutableStateOf(0) }
    val listHistory = remember { mutableStateListOf<HistoryModel>() }

    var buttonClicked by remember { mutableStateOf(false) }
    var dataReceived by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    transactionVM.loadTransactionHistory("Bearer ${AppPreferencesManager.getString(context, GlobalUtils.TOKEN_KEY, "")}")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    println("Screen paused")
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // Elemen yang dikontrol Constraint
        val (header, list) = createRefs()

        // Header
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Riwayat Pembayaran",
                    color = AppTheme.colors.surface,
                    style = AppTheme.typography.titleLarge
                )
                Text(
                    text = "$approvedCount Approved | $settledCount Settled",
                    color = AppTheme.colors.surface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Button(
                onClick = {
                    buttonClicked = true
                },
                enabled = approvedCount > 0, // hanya aktif jika Approved tersedia
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (approvedCount > 0) Color(0xFF2196F3) else Color.LightGray
                ),
            ) {
                Text(
                    text = "Settle Now",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        // button action
        LaunchedEffect(buttonClicked) {
            if (buttonClicked) {
                // update history
                transactionVM.postSettlement(
                    "Bearer ${AppPreferencesManager.getString(context, GlobalUtils.TOKEN_KEY, "")}",
                )
            }
        }

        // Update data
        when (val s = settlementState.value) {
            is UIStateResponse.Loading -> {
                if (buttonClicked) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    )
                }
            }
            is UIStateResponse.Error -> {
                buttonClicked = false
                transactionVM.clearStateSettlement()
                Toast.makeText(context, "$s.message", Toast.LENGTH_SHORT).show()
            }
            is UIStateResponse.Success -> {
                buttonClicked = false
                transactionVM.clearStateSettlement()
                // Load data
                dataReceived = false
                Toast.makeText(context, "${s.response?.records_updated} ${s.response?.message}", Toast.LENGTH_SHORT).show()
                transactionVM.loadTransactionHistory("Bearer ${AppPreferencesManager.getString(context, GlobalUtils.TOKEN_KEY, "")}")
            }
            else -> Unit
        }

        /** Daftar transaksi */
        LaunchedEffect(dataReceived) {
            if (!dataReceived) {
                // Load data
                transactionVM.loadTransactionHistory("Bearer ${AppPreferencesManager.getString(context, GlobalUtils.TOKEN_KEY, "")}")
            }
        }

        when (val s = historyState.value) {
            is UIStateResponse.Loading -> {
                if (!dataReceived) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    )
                }
            }
            is UIStateResponse.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text(text = "Error: ${s.message}")
                }
                dataReceived = true
            }
            is UIStateResponse.Success -> {
                val history = s.response
                dataReceived = true
                settledCount = history.count { item -> item.status == "settled" }
                approvedCount = history.count { item -> item.status == "approved" }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(list) {
                            top.linkTo(header.bottom, margin = 12.dp)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            height = androidx.constraintlayout.compose.Dimension.fillToConstraints
                        }
                ) {
                    items(history) { history ->
                        HistoryItems(
                            id = history.id.toString(),
                            merchant = history.merchantId,
                            amount = GlobalUtils.idrFormat(history.amount),
                            card = history.cardNumber,
                            status = history.status,
                            onSettleClick = {
                                /**/
                            }
                        )
                    }
                }
            }
            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        HistoryScreen(context, navHostController)
    }
}