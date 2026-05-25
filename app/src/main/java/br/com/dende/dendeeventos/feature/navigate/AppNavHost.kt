package br.com.dende.dendeeventos.feature.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.dende.dendeeventos.feature.event.AtivarEventoAvisoDialog
import br.com.dende.dendeeventos.feature.event.AtivarEventoOkDialog
import br.com.dende.dendeeventos.feature.event.DesativarEventoAvisoDialog
import br.com.dende.dendeeventos.feature.event.DesativarEventoOkDialog

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.AtivarEventoOkRoute
    ) {
        composable<AppDestinations.AtivarEventoRoute> { backStackEntry ->
            val route: AppDestinations.AtivarEventoRoute = backStackEntry.toRoute()
            AtivarEventoAvisoDialog(
                eventoId = route.eventoId,
                onDismiss = { navController.popBackStack() },
                onSucesso = {
                    navController.navigate(AppDestinations.AtivarEventoOkRoute) {
                        popUpTo<AppDestinations.AtivarEventoRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<AppDestinations.AtivarEventoOkRoute> {
            AtivarEventoOkDialog(
                onEntendiClick = { navController.popBackStack() }
            )
        }

        composable<AppDestinations.DesativarEventoRoute> { backStackEntry ->
            val route: AppDestinations.DesativarEventoRoute = backStackEntry.toRoute()
            DesativarEventoAvisoDialog(
                eventoId = route.eventoId,
                onDismiss = { navController.popBackStack() },
                onSucesso = {
                    navController.navigate(AppDestinations.DesativarEventoOkRoute) {
                        popUpTo<AppDestinations.DesativarEventoRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<AppDestinations.DesativarEventoOkRoute> {
            DesativarEventoOkDialog(
                onEntendiClick = { navController.popBackStack() }
            )
        }
    }
}
