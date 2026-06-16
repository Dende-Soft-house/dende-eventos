package br.com.dende.dendeeventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.home.HomeScreen
import br.com.dende.dendeeventos.ui.login.LoginScreen
import br.com.dende.dendeeventos.ui.theme.FeedEventosScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Home
    ) {
        composable(AppDestinations.Home) {
            HomeScreen(navController = navController)
        }

        composable(AppDestinations.Login) {
            LoginScreen(navController = navController)
        }

        composable(AppDestinations.FeedEventos) {
            FeedEventosScreen()
        }
    }
}