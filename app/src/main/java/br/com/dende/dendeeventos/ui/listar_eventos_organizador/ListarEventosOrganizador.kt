package br.com.dende.dendeeventos.ui.listar_eventos_organizador

import android.annotation.SuppressLint
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusEventosScreen(
    onBackClick: () -> Unit,
    onEventClick: (String) -> Unit,
    onAddEventClick: () -> Unit,
    viewModel: ListarEventosOrganizadorViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("AGENDADOS", "INATIVOS")

    LaunchedEffect(Unit) {
        viewModel.carregarEventosDoOrganizador(1L)
    }

    val eventosExibidos = if (selectedTab == 0) {
        uiState.eventos.filter { it.status.name == "ATIVO" }
    } else {
        uiState.eventos.filter { it.status.name == "INATIVO" }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Meus Eventos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = Inter,
                        color = Black
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
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Orange)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(eventosExibidos) { evento ->
                        OrganizerEventCard(
                            modifier = Modifier.clickable { onEventClick(evento.eventoId.toString()) },
                            title = evento.nome,
                            location = evento.local,
                            dataAcima = "Mês",
                            dataAbaixo = "Dia"
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MeusEventosScreenPreview() {
    MeusEventosScreen({}, {}, {}, ListarEventosOrganizadorViewModel())
}