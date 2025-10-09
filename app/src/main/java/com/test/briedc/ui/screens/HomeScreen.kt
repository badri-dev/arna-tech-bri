package com.test.briedc.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.test.briedc.ui.components.BottomNavBar
import com.test.briedc.ui.screens.home.HistoryScreen
import com.test.briedc.ui.screens.home.SaleScreen
import com.test.briedc.ui.screens.home.SettlementScreen
import com.test.briedc.ui.theme.AppTheme
import com.test.briedc.utils.GlobalUtils

@Composable
fun HomeScreen(context: Context, navHostController: NavHostController) {

    var selectedIndex by remember { mutableStateOf(0) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, GlobalUtils.statusBarPadding, 0.dp, GlobalUtils.navigationBarPadding)
            .background(Color.White)
    ) {
        // Instancing Component
        val (content, bottomBar) = createRefs()

        //Main Content
        Box(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBar.top)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            when (selectedIndex) {
                0 -> SaleScreen(context, navHostController)
                1 -> HistoryScreen(context, navHostController)
                2 -> SettlementScreen(context, navHostController)
            }
        }

        // Bottom Navigation
        BottomNavBar(
            selectedIndex = selectedIndex,
            onItemSelected = { selectedIndex = it },
            modifier = Modifier.constrainAs(bottomBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        val context = LocalContext.current
        val navHostController = NavHostController(context)
        HomeScreen(context, navHostController)
    }
}