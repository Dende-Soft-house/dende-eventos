package br.com.dende.dendeeventos.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.cadastro.CadastrarOrganizadorScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Tela de Login (Feito pelos nossos colegas da Equipe ULM)")
                Button(onClick = {
                    navController.navigate("cadastro")
                }) {
                    Text("Ir para Criar Conta")
                }
            }
        }

        composable("cadastro") {
            CadastrarOrganizadorScreen(
                onVoltarParaLogin = {
                    navController.popBackStack()
                }
            )
        }

    }
}