package br.com.dende.dendeeventos.ui.cancelamento.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.theme.DividerColor
import br.com.dende.dendeeventos.ui.theme.TextPrimary
import br.com.dende.dendeeventos.ui.theme.TextSecondary

@Composable
fun CancelProgressBar(
    currentStep: Int,
    totalSteps: Int = 3,
    completed: Boolean = false
) {
    val progress = if (completed) 1f else currentStep.toFloat() / totalSteps.toFloat()
    
    val text = if (completed) {
        "Passo $totalSteps de $totalSteps ✓"
    } else {
        "Passo $currentStep de $totalSteps"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(DividerColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(2.dp))
                    .background(TextPrimary)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = TextSecondary,
            fontSize = 12.sp
        )
    }
}
