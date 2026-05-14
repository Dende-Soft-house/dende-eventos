package br.com.dende.dendeeventos.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.dende.dendeeventos.R
import br.com.dende.dendeeventos.core.designsystem.components.CategoryChip
import br.com.dende.dendeeventos.core.designsystem.components.EventCard
import androidx.compose.ui.tooling.preview.Preview
import br.com.dende.dendeeventos.core.designsystem.components.BottomNavBar
import androidx.compose.ui.draw.scale
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Hoje") }

    Scaffold(
        bottomBar = {
            //
            BottomNavBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            //
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("Pesquisar eventos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium
            )

            //
            Row(
                modifier = Modifier
                    .fillMaxWidth() //
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween, // Joga o título para a esquerda e os botões para a direita
                verticalAlignment = Alignment.CenterVertically // Centraliza para não ficarem tortos
            ) {
                Text(
                    text = "",
                    style = MaterialTheme.typography.titleSmall
                )


                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
                ) {
                    CategoryChip(
                        label = "Hoje",
                        iconRes = R.drawable.ic_launcher_foreground,
                        isSelected = selectedFilter == "Hoje",
                        onClick = { selectedFilter = "Hoje" },
                        modifier = Modifier.scale(0.85f) // Diminui o botão
                    )

                    CategoryChip(
                        label = "Agendados",
                        iconRes = R.drawable.ic_launcher_foreground,
                        isSelected = selectedFilter == "Gratuito",
                        onClick = { selectedFilter = "Gratuito" },
                        modifier = Modifier.scale(0.85f) // Diminui o botão
                    )
                }
            }

            //
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(5) {
                    //
                    EventCard(
                        title = "IntegraSI",
                        date = "14 Mai",
                        location = "UNEX , BA",
                        price = "Grátis",
                        time = "19:00",
                    )
                }
            }
        }
    }
}