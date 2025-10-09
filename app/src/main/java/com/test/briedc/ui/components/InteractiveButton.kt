package com.test.briedc.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.briedc.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun InteractiveButton(
    text: String,
    startDefaultColor: Color,
    endDefaultColor: Color,
    startPressedColor: Color,
    endPressedColor: Color,
    textColorDefault: Color,
    textColorPressed: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    // LaunchedEffect is Composable, so for using this tools we need to write this command inside Composable also
    // and .clickable.onClick from Modifier isn't Composable
    if (isPressed) {
        LaunchedEffect(Unit) {
            delay(150)
            isPressed = false
        }
    }

    // Animation when button clicked
    val animatedGradient = Brush.linearGradient(
        colors = if (isPressed)
            listOf(startPressedColor, endPressedColor)
        else
            listOf(startDefaultColor, endDefaultColor)
    )

    val animatedTextColor by animateColorAsState(
        targetValue = if (isPressed) textColorPressed else textColorDefault,
        label = "textColorAnim"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(25))
            .background(animatedGradient, RoundedCornerShape(25))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    isPressed = true
                    onClick()
                }
            )
            .padding(vertical = 14.dp, horizontal = 28.dp)
    ) {
        Text(
            text = text,
            color = animatedTextColor,
            style = AppTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InteractiveButtonPreview() {
    AppTheme {
        InteractiveButton(
            text = "Lorem Ipsum",
            startDefaultColor = AppTheme.colors.surface,
            endDefaultColor = AppTheme.colors.onSurface,
            startPressedColor = AppTheme.colors.onSurface,
            endPressedColor = AppTheme.colors.surface,
            textColorDefault = Color.White,
            textColorPressed = AppTheme.colors.background
        ) {
            /* do something */
        }
    }
}