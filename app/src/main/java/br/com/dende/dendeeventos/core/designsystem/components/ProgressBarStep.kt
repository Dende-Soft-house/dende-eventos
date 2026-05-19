package br.com.dende.dendeeventos.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.dende.dendeeventos.ui.theme.Black
import br.com.dende.dendeeventos.ui.theme.Grey2

@Composable
fun ProgressBarStep(step: Int, totalSteps: Int) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)) {
        if (step > 0) {
            Box(
                modifier = Modifier
                    .weight(step.toFloat())
                    .fillMaxHeight()
                    .background(Black)
            )
        }

        val passosRestantes = totalSteps - step
        if (passosRestantes > 0) {
            Box(
                modifier = Modifier
                    .weight(passosRestantes.toFloat())
                    .fillMaxHeight()
                    .background(Grey2)
            )
        }
    }
}