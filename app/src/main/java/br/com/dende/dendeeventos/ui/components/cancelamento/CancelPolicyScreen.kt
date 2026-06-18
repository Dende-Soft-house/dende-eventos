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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelInfoRow
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelProgressBar
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelTopBar
import br.com.dende.dendeeventos.ui.cancelamento.components.MiniTicketCard
import br.com.dende.dendeeventos.ui.cancelamento.components.PrimaryGradientButton
import br.com.dende.dendeeventos.ui.cancelamento.components.SecondaryOutlineButton
import br.com.dende.dendeeventos.ui.theme.OrangeLight
import br.com.dende.dendeeventos.ui.theme.OrangePrimary
import br.com.dende.dendeeventos.ui.theme.ScreenBackground
import br.com.dende.dendeeventos.ui.theme.TextPrimary
import br.com.dende.dendeeventos.ui.theme.TextSecondary

@Composable
fun CancelPolicyScreen(
    state: TicketCancelUiState,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    onKeepTicketClick: () -> Unit
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
                SecondaryOutlineButton(text = "CONTINUAR", onClick = onContinueClick)
                PrimaryGradientButton(text = "MANTER MEU INGRESSO", onClick = onKeepTicketClick)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CancelTopBar(title = "Política de Cancelamento", onBackClick = onBackClick)
            CancelProgressBar(currentStep = 1)
            
            Spacer(modifier = Modifier.height(16.dp))
            MiniTicketCard(
                eventName = state.eventName,
                eventDate = state.eventDate,
                eventTime = state.eventTime,
                seat = state.seat
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            // Alert Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(OrangeLight)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⚠",
                        color = OrangePrimary,
                        fontSize = 16.sp
                    )
                    Text(
                        text = " Leia antes de cancelar",
                        color = OrangePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Solicitações de cancelamento estão sujeitas à taxa de 30% sobre o valor total pago. Após a confirmação, o valor restante será enviado para reembolso.",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Prazo de Reembolso",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = state.refundPeriod,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Seu Ingresso",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            CancelInfoRow(label = "Data do Evento", value = state.eventDate)
            CancelInfoRow(label = "Dias Restantes", value = state.daysRemaining)
            CancelInfoRow(label = "Reembolso Elegível", value = state.originalAmount, showDivider = false)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
