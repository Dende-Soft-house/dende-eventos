package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.theme.Black
import br.com.dende.dendeeventos.ui.theme.Error
import br.com.dende.dendeeventos.ui.theme.Grey2
import br.com.dende.dendeeventos.ui.theme.SoftDarkish

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    placeholder,
                    color = SoftDarkish,
                    fontSize = 15.sp,
                    fontFamily = Inter
                )
            },
            trailingIcon = {
                when {
                    isError -> Icon(Icons.Default.Warning, contentDescription = null, tint = Error)
                    icon != null -> Icon(icon, contentDescription = null, tint = Black)
                }
            },
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Black,
                unfocusedBorderColor = Grey2,
                errorBorderColor = Error,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Black
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Error,
                fontSize = 12.sp,
                fontFamily = Inter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 8.dp),
                textAlign = TextAlign.End
            )
        }
    }
}