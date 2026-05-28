package br.com.dende.dendeeventos.feature.event

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Teste(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.navigate(
                br.com.dende.dendeeventos.feature.navigate.AppDestinations.AtivarEventoRoute(
                    eventoId = "evento-teste-123"
                )
            )
        }) {
            Text("Testar Ativar Evento")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(
                br.com.dende.dendeeventos.feature.navigate.AppDestinations.DesativarEventoRoute(
                    eventoId = "evento-teste-123"
                )
            )
        }) {
            Text("Testar Desativar Evento")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTesteNavegacaoScreen() {
    Teste(navController = rememberNavController())
}
