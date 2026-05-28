package br.com.dende.dendeeventos.ui.theme

import androidx.compose.foundation.background
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

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import br.com.dende.dendeeventos.R

import br.com.dende.dendeeventos.core.designsystem.components.BottomNavBar
import br.com.dende.dendeeventos.core.designsystem.components.TicketCard
import br.com.dende.dendeeventos.core.designsystem.components.TopTabButton

// ======================================================
// ATIVOS
// ======================================================

@Composable
fun ListarIngressosAtivos() {

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedIndex = 2)
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F3F3))
        ) {

            // HEADER
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

                // USER
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
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Text(
                        text = "Olá, Usuário",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // TABS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    TopTabButton(
                        text = "Ativos",
                        selected = true
                    )

                    TopTabButton(
                        text = "Encerrados",
                        selected = false
                    )
                }
            }

            // LISTA
            LazyColumn(
                modifier = Modifier.padding(padding),

                contentPadding = PaddingValues(
                    top = 200.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 120.dp
                ),

                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                items(2) {

                    TicketCard(
                        titulo = "IntegraSI FSA",
                        data = "21 Abr, 18:50",
                        status = "ATIVO",
                        statusColor = Color(0xFF169B16),
                        backgroundStatus = Color(0xFFDFF5D8),
                        buttonColor = Color(0xFF1F2230),
                        buttonTextColor = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListarIngressosAtivosPreview() {

    ListarIngressosAtivos()
}

// ======================================================
// ENCERRADOS
// ======================================================

@Composable
fun ListarIngressosEncerrados() {

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedIndex = 2)
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F3F3))
        ) {

            // HEADER
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

                // USER
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
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Text(
                        text = "Olá, Usuário",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // TABS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    TopTabButton(
                        text = "Ativos",
                        selected = false
                    )

                    TopTabButton(
                        text = "Encerrados",
                        selected = true
                    )
                }
            }

            // LISTA
            LazyColumn(
                modifier = Modifier.padding(padding),

                contentPadding = PaddingValues(
                    top = 200.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 120.dp
                ),

                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                item {

                    TicketCard(
                        titulo = "Hackathon",
                        data = "20 Mar, 10:00",
                        status = "ENCERRADO",
                        statusColor = Color(0xFFC62828),
                        backgroundStatus = Color(0xFFF8D7DA),
                        buttonColor = Color(0xFFA6A6A6),
                        buttonTextColor = Color(0xFFEDEDED)
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    TicketCard(
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

@Preview(showBackground = true)
@Composable
fun ListarIngressosEncerradosPreview() {

    ListarIngressosEncerrados()
}