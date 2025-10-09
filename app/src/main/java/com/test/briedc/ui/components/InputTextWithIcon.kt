package com.test.briedc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.briedc.ui.theme.AppTheme

@Composable
fun InputTextWithIcon(
    value: String,
    placeHolder : String,
    keyboardOptions: KeyboardOptions,
    icon : ImageVector,
    iconColor: Color,
    cursorColor: Color,
    isPassword: Boolean,
    isError: Boolean,
    onResultValue: (String) -> Unit
) {

    var isVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onResultValue(it) },
        keyboardOptions = keyboardOptions,
        placeholder = { Text(placeHolder) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )
        },
        // trailingIcon By Default has null but we can set this value if we need to create toggle password
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            }
        },
        singleLine = true,
        // visualTansformation by Default has None but we can set this if we need transformation visual for toggle password icon
        visualTransformation =
            if (isPassword) {
                if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = cursorColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp)),
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun InputTextWithIconPreview() {
    InputTextWithIcon(
        value = "",
        placeHolder = "Lorem ipsum..",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        icon = Icons.Default.Email,
        iconColor = AppTheme.colors.background,
        cursorColor = AppTheme.colors.surface,
        isPassword = false,
        isError = false
    ) { _ -> }
}