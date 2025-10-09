package com.test.briedc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.briedc.ui.theme.AppTheme

@Composable
fun HistoryItems(
    id: String,
    merchant: String,
    amount: String,
    card: String,
    status: String,
    onSettleClick: () -> Unit
) {
    val statusColor = when (status) {
        "approved" -> Color(0xFF4CAF50) // 🟢
        "settled" -> Color(0xFFBDBDBD) // ⚪ abu-abu
        else -> Color.Gray
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header row: ID & status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: $id",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1976D2)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(statusColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = status,
                        color = statusColor,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Merchant
            Text(
                text = merchant,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            // Amount
            Text(
                text = "Amount: $amount",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )

            // Card
            Text(
                text = "Card: $card",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Button
//            Button(
//                onClick = onSettleClick,
//                enabled = status == "approved", // hanya aktif jika Approved
//                shape = RoundedCornerShape(50),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (status == "approved") Color(0xFF2196F3) else Color.LightGray
//                ),
//                modifier = Modifier.align(Alignment.End)
//            ) {
//                Text(
//                    text = "Settle Now",
//                    color = Color.White,
//                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
//                )
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryItemsPreview() {
    AppTheme {
        HistoryItems(
            "2",
            "mmm2134432",
            "90000",
            "3242 32432 423423 432",
            "Approved"
        ) {}
    }
}