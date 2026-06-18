package br.com.dende.dendeeventos.ui.listar_eventos_organizador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dende.dendeeventos.core.designsystem.components.OrganizerEventCard
import br.com.dende.dendeeventos.core.designsystem.theme.Inter
import br.com.dende.dendeeventos.ui.theme.Black
import br.com.dende.dendeeventos.ui.theme.Orange
import br.com.dende.dendeeventos.ui.theme.SoftDarkish
import br.com.dende.dendeeventos.ui.theme.White

data class EventoListagem(
    val id: String,
    val nome: String,
    val local: String,
    val dataAcima: String,
    val dataAbaixo: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusEventosScreen(
    onBackClick: () -> Unit, onEventClick: (String) -> Unit, onAddEventClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("AGENDADOS", "INATIVOS")

    val eventosAgendados = listOf(
        EventoListagem("1", "IntegraSI 2026.1", "CENTRO UNIVERSITÁRIO DE EXCELÊNCIA (UNEX)", "Março", "14"),
        EventoListagem("2", "Integra SI - Hackathon", "CENTRO UNIVERSITÁRIO DE EXCELÊNCIA (UNEX)", "Março", "17")
    )

    val eventosInativos = listOf(
        EventoListagem("3", "IntegraSI 2025.2", "CENTRO UNIVERSITÁRIO DE EXCELÊNCIA (UNEX)", "Setembro", "2025"),
        EventoListagem("4", "IntegraSI 2025.1", "CENTRO UNIVERSITÁRIO DE EXCELÊNCIA (UNEX)", "Março", "2025"),
        EventoListagem("5", "IntegraSI 2024.2", "CENTRO UNIVERSITÁRIO DE EXCELÊNCIA (UNEX)", "Agosto", "2024")
    )

    val eventosExibidos = if (selectedTab == 0) eventosAgendados else eventosInativos

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                Text(
                    "Meus Eventos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = Inter
                )
            }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Black
                    )
                }
            }, actions = {
                IconButton(onClick = onAddEventClick) {
                    Icon(Icons.Default.Add, contentDescription = "Novo Evento", tint = Black)
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 6.dp)
                    .padding(4.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    val isActive = selectedTab == index

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(28.dp))
                            .background(if (isActive) Orange else Color.Transparent)
                            .clickable { selectedTab = index }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = title,
                            fontFamily = Inter,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                            color = if (isActive) White else SoftDarkish,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(eventosExibidos) { evento ->
                    OrganizerEventCard(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onEventClick(evento.id) },
                        title = evento.nome,
                        location = evento.local,
                        dataAcima = evento.dataAcima,
                        dataAbaixo = evento.dataAbaixo
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeusEventosScreenPreview() {
    MeusEventosScreen({}, {}, {})
}