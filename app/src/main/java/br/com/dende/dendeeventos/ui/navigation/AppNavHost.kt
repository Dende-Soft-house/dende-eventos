package br.com.dende.dendeeventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.theme.EventosDetalhesScreen
import br.com.dende.dendeeventos.ui.theme.FeedEventosScreen

@Composable
fun AppNavHost() {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.FEED
    ) {

        composable(AppDestinations.FEED) {
            FeedEventosScreen(
                navController = navController
            )
        }

        composable(
            route = AppDestinations.DETALHES
        ) { backStackEntry ->

            val eventId =
                backStackEntry.arguments
                    ?.getString("eventoId")
                    ?.toLongOrNull()

            EventosDetalhesScreen(
                eventId = eventId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}