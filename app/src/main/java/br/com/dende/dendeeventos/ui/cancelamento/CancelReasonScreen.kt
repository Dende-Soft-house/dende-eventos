package br.com.dende.dendeeventos.ui.cancelamento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelProgressBar
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelTopBar
import br.com.dende.dendeeventos.ui.cancelamento.components.PrimaryGradientButton
import br.com.dende.dendeeventos.ui.cancelamento.components.ReasonOptionItem
import br.com.dende.dendeeventos.ui.cancelamento.components.SecondaryOutlineButton
import br.com.dende.dendeeventos.ui.theme.Orange

@Composable
fun CancelReasonScreen(
    uiState: CancelamentoUiState,
    onReasonSelected: (String) -> Unit,
    onObservationChanged: (String) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = ScreenBackground,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ScreenBackground)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryGradientButton(text = "CONTINUAR", onClick = onContinueClick)
                SecondaryOutlineButton(text = "VOLTAR", onClick = onBackClick)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CancelTopBar(title = "Motivo do Cancelamento", onBackClick = onBackClick)
            CancelProgressBar(currentStep = 2)

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Por favor, selecione o motivo do cancelamento",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            CancelReason.values().forEach { reason ->
                ReasonOptionItem(
                    text = reason.label,
                    selected = uiState.motivoSelecionado == reason.label,
                    onClick = { onReasonSelected(reason.label) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Observações adicionais (opcional)",
                color = TextSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.observacao,
                onValueChange = { onObservationChanged(it) },
                placeholder = {
                    Text(
                        text = "Conte-nos mais sobre o seu motivo...",
                        color = Color(0xFF6F6F6F),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color(0xFF111111),
                    fontSize = 14.sp
                ),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFF111111),
                    unfocusedTextColor = Color(0xFF111111),
                    cursorColor = Orange,
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
