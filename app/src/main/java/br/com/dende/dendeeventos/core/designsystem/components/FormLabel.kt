package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.theme.Inter

@Composable
fun FormLabel(label: String, isRequired: Boolean) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = Inter
            )) {
                append(label)
            }
            if (isRequired) {
                withStyle(style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFFD32F2F)
                )) {
                    append("*")
                }
            }
        },
        modifier = Modifier.padding(bottom = 8.dp)
    )
}