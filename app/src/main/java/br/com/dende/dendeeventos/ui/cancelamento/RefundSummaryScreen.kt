package br.com.dende.dendeeventos.ui.cancelamento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelConfirmationSheet
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelInfoRow
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelProgressBar
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelTopBar
import br.com.dende.dendeeventos.ui.cancelamento.components.MiniTicketCard
import br.com.dende.dendeeventos.ui.cancelamento.components.PrimaryGradientButton
import br.com.dende.dendeeventos.ui.cancelamento.components.SecondaryOutlineButton
import br.com.dende.dendeeventos.ui.theme.Orange

@Composable
fun RefundSummaryScreen(
    state: TicketCancelUiState,
    uiState: CancelamentoUiState,
    onConfirmClick: () -> Unit,
    onDismissConfirmation: () -> Unit,
    onConfirmCancellation: () -> Unit,
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
                PrimaryGradientButton(
                    text = "CONFIRMAR CANCELAMENTO",
                    onClick = onConfirmClick
                )
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
            CancelTopBar(title = "Resumo do Reembolso", onBackClick = onBackClick)
            CancelProgressBar(currentStep = 3, completed = true)

            Spacer(modifier = Modifier.height(16.dp))
            MiniTicketCard(
                eventName = state.eventName,
                eventDate = state.eventDate,
                eventTime = state.eventTime,
                seat = state.seat
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Detalhes do Pedido",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            CancelInfoRow(label = "Evento", value = state.eventName)
            CancelInfoRow(label = "Valor Original", value = state.originalAmount)
            CancelInfoRow(
                label = "Taxa de Cancelamento",
                value = state.cancelFee,
                valueColor = Orange,
                showDivider = false
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(TextPrimary)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reembolso Total",
                        color = ScreenBackground,
                        fontSize = 14.sp
                    )
                    Text(
                        text = state.refundAmount,
                        color = ScreenBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Motivo: ${state.selectedReason.label}",
                color = TextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Bottom sheet de confirmação
    if (uiState.exibirConfirmacao) {
        CancelConfirmationSheet(
            refundAmount = state.refundAmount,
            onConfirm = onConfirmCancellation,
            onKeepTicket = onDismissConfirmation,
            onDismiss = onDismissConfirmation
        )
    }
}

