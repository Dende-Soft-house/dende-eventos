package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarStep(step: Int, totalSteps: Int) {
    Row(modifier = Modifier.fillMaxWidth().height(4.dp)) {
        Box(modifier = Modifier.weight(step.toFloat()).fillMaxHeight().background(Color.Black))
        Box(modifier = Modifier.weight((totalSteps - step).toFloat()).fillMaxHeight().background(Color(0xFFE0E0E0)))
    }
}