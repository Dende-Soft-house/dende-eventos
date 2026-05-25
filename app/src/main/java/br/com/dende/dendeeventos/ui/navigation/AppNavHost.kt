package br.com.dende.dendeeventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.theme.EventosDetalhesScreen
import br.com.dende.dendeeventos.ui.theme.FeedEventosScreen

@Composable
fun AppNavHost() {

    //Responsável pela navegação da tela
    val navController =
        rememberNavController()

    //Define as rotas da aplicação
    NavHost(
        navController = navController,
        startDestination = AppDestinations.FEED
    ) {

        //Feed de eventos
        composable(AppDestinations.FEED) {
            FeedEventosScreen(
                navController = navController
            )
        }

        //Tela de detalhe de evento
        composable(
            route = AppDestinations.DETALHES
        ) { backStackEntry ->

            // Pega o ID do evento enviado pela navegação
            val eventId =
                backStackEntry.arguments
                    ?.getString("eventoId")
                    ?.toLongOrNull()

            EventosDetalhesScreen(
                eventId = eventId,
                //Retorna pra tela anterior
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}