package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopTabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (selected)
                    Color.White
                else
                    Color.Transparent
            )
            .clickable { onClick() }
            .padding(
                horizontal = 28.dp,
                vertical = 6.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color =
                if (selected)
                    Color(0xFFFF6A00)
                else
                    Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
