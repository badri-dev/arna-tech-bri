package com.test.briedc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.test.briedc.ui.theme.AppTheme

@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(26.dp),
                clip = false // kalau true, konten di dalam ikut di-clip
            )
            .clip(
                RoundedCornerShape(
                    topStart = 26.dp,
                    topEnd = 26.dp,
                    0.dp,
                    0.dp
                )
            )
            .background(Color.White)
    ) {
        val (sale, history, settlement) = createRefs()

        // buat horizontal chain agar rata
        createHorizontalChain(
            sale, history, settlement,
            chainStyle = ChainStyle.Spread
        )

        BottomNavItem(
            title = "Sale",
            icon = Icons.Default.ShoppingCart,
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) },
            modifier = Modifier.constrainAs(sale) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        BottomNavItem(
            title = "History",
            icon = Icons.Default.History,
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            modifier = Modifier.constrainAs(history) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        BottomNavItem(
            title = "Settlement",
            icon = Icons.Default.Settings,
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            modifier = Modifier.constrainAs(settlement) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    AppTheme {
        BottomNavBar(
            selectedIndex = 0,
            onItemSelected = {},
            modifier = Modifier
        )
    }
}