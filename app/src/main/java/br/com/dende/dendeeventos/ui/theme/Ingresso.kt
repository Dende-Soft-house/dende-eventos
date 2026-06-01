package br.com.dende.dendeeventos.ui.theme

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.ui.viewmodels.IngressoViewModel

@Composable
fun IngressoScreen(
    viewModel: IngressoViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F3F3))
                .padding(padding)
                .padding(horizontal = 22.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {

                    Icon(
                        painter = painterResource(
                            id = R.drawable.icon_arrow
                        ),
                        contentDescription = "Botão de Voltar",
                        tint = Color(0xFFFF6A00),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Ingresso",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF232330),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.banner_card
                    ),
                    contentDescription = "Banner do Evento",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = state.titulo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF232330)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = state.descricao,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEDEDED)
                )
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    TicketInfoRow(
                        icon = R.drawable.icon_calendar,
                        text = state.dataHora
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TicketInfoRow(
                        icon = R.drawable.icon_map_pin,
                        text = state.local
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TicketInfoRow(
                        icon = R.drawable.icon_ticket_ticket,
                        text = state.tipoEntrada
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TicketInfoRow(
                        icon = R.drawable.icon_user_ticket,
                        text = state.nomeTitular
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.qrcode
                            ),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(180.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BOTÕES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // BAIXAR
                Button(
                    onClick = { viewModel.baixarIngresso() },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F2230)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Baixar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // CANCELAR
                OutlinedButton(
                    onClick = { viewModel.cancelarIngresso() },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = 2.5.dp,
                        color = Color.Black
                    )
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TicketInfoRow(
    icon: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color(0xFFFF6A00),
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            color = Color(0xFF232330)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun IngressoScreenPreview() {
    IngressoScreen()
}