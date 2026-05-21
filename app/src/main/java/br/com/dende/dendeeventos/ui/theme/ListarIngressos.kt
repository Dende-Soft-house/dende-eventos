package br.com.dende.dendeeventos.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

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
import androidx.lifecycle.viewmodel.compose.viewModel // IMPORTANTE: Import do ViewModel no Compose

import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.components.BottomBar
import br.com.dende.dendeeventos.core.designsystem.components.EventCard
import br.com.dende.dendeeventos.ui.theme.viewmodels.AbaIngressos // Import correto
import br.com.dende.dendeeventos.ui.theme.viewmodels.ListarIngressosViewModel

@Composable
fun ListarIngressos(
    viewModel: ListarIngressosViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBar()
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF3F3F3))
        ) {
            // HEADER LARANJA
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(CircleShape)
                            .background(
                                Color.White.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            painter = painterResource(
                                id = R.drawable.icon_user
                            ),
                            contentDescription = "Foto do Usuário",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Text(
                        text = "Olá, ${uiState.nomeUsuario}",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    // COLUNA 1: ATIVOS
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { viewModel.selecionarAba(AbaIngressos.ATIVOS) }
                    ) {
                        Text(
                            text = "Ativos",
                            color = if (uiState.abaSelecionada == AbaIngressos.ATIVOS) Color.White else Color.White.copy(alpha = 0.6f),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        if (uiState.abaSelecionada == AbaIngressos.ATIVOS) {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(3.dp)
                                    .background(Color.White)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(3.dp))
                        }
                    } // Fim da Coluna Ativos

                    // COLUNA 2: ENCERRADOS (Agora está fora da coluna Ativos)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { viewModel.selecionarAba(AbaIngressos.ENCERRADOS) }
                    ) {
                        Text(
                            text = "Encerrados",
                            // Cor dinâmica baseada na aba selecionada
                            color = if (uiState.abaSelecionada == AbaIngressos.ENCERRADOS) Color.White else Color.White.copy(alpha = 0.6f),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        if (uiState.abaSelecionada == AbaIngressos.ENCERRADOS) {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(3.dp)
                                    .background(Color.White)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(3.dp))
                        }
                    } // Fim da Coluna Encerrados
                }
            }

            // LISTA DE EVENTOS
            LazyColumn(
                contentPadding = PaddingValues(top = 200.dp, start = 20.dp, end = 20.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(uiState.ingressosExibidos) { ingresso ->
                    EventCard(
                        titulo = ingresso.titulo,
                        local = ingresso.local,
                        isAtivo = ingresso.isAtivo // <- Só adicionar esta linha!
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ListarIngressosPreview() {
    ListarIngressos()
}