package br.com.dende.dendeeventos.ui.theme.viewmodels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.components.BottomNavBar
import br.com.dende.dendeeventos.core.designsystem.components.TicketCard
import br.com.dende.dendeeventos.core.designsystem.components.TopTabButton
import br.com.dende.dendeeventos.ui.theme.viewmodels.ListarIngressosViewModel

@Composable
fun ListarIngressosScreen(
    viewModel: ListarIngressosViewModel = viewModel(),
    onNavigateToIngresso: () -> Unit = {}
) {
    val abaSelecionada by viewModel.abaSelecionada.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedIndex = 2)
        }
    ) { padding ->

        // Fundo cinza de toda a tela
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F3F3))
        ) {

            // A LISTA AGORA CONTROLA TUDO (Header + Cartões)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 120.dp), // Espaço no fundo para a NavBar
                verticalArrangement = Arrangement.spacedBy(18.dp) // Espaçamento automático entre os itens
            ) {

                // 1. O HEADER AGORA É O PRIMEIRO ITEM DA LISTA (Ele vai rolar junto!)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(
                                color = Color(0xFFFF6A00),
                                shape = RoundedCornerShape(
                                    bottomStart = 28.dp,
                                    bottomEnd = 28.dp
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        // USER INFO
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_user),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text = "Olá, Usuário",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // TABS COM CLIQUE
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TopTabButton(
                                text = "Ativos",
                                selected = abaSelecionada == 0,
                                onClick = { viewModel.selecionarAba(0) }
                            )

                            TopTabButton(
                                text = "Encerrados",
                                selected = abaSelecionada == 1,
                                onClick = { viewModel.selecionarAba(1) }
                            )
                        }
                    }
                }

                // 2. OS CARTÕES VÊM LOGO A SEGUIR
                if (abaSelecionada == 0) {
                    items(2) {
                        TicketCard(
                            modifier = Modifier.padding(horizontal = 20.dp), // Adiciona margem lateral aos cartões
                            titulo = "IntegraSI FSA",
                            data = "21 Abr, 18:50",
                            status = "ATIVO",
                            statusColor = Color(0xFF169B16),
                            backgroundStatus = Color(0xFFDFF5D8),
                            buttonColor = Color(0xFF1F2230),
                            buttonTextColor = Color.White,
                            onVerClick = onNavigateToIngresso
                        )
                    }
                } else {
                    item {
                        TicketCard(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            titulo = "Hackathon",
                            data = "20 Mar, 10:00",
                            status = "ENCERRADO",
                            statusColor = Color(0xFFC62828),
                            backgroundStatus = Color(0xFFF8D7DA),
                            buttonColor = Color(0xFFA6A6A6),
                            buttonTextColor = Color(0xFFEDEDED)
                        )
                    }
                    item {
                        TicketCard(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            titulo = "Workshop Figma",
                            data = "15 Fev, 14:00",
                            status = "CANCELADO",
                            statusColor = Color(0xFF7A7A7A),
                            backgroundStatus = Color(0xFFE0E0E0),
                            buttonColor = Color(0xFFA6A6A6),
                            buttonTextColor = Color(0xFFEDEDED)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListarIngressosScreenPreview() {
    ListarIngressosScreen()
}