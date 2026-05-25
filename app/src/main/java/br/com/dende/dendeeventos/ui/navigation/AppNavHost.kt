package br.com.dende.dendeeventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.domain.Evento
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.BannerScreen
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.FaturamentoScreen
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.InformacoesAdicionaisScreen
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.InformacoesBasicasScreen
import br.com.dende.dendeeventos.ui.listar_eventos_organizador.MeusEventosScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    var eventoAlterando by remember { mutableStateOf<Evento?>(null) }

    NavHost(navController = navController, startDestination = AppDestinations.LISTAR_EVENTOS) {
        composable(AppDestinations.LISTAR_EVENTOS) {
            MeusEventosScreen(
                onBackClick = { navController.popBackStack() },
                onEventClick = {
                    navController.navigate(AppDestinations.INFORMACOES_BASICAS)
                },
                onAddEventClick = {
                    eventoAlterando = null
                    navController.navigate(AppDestinations.INFORMACOES_BASICAS)
                }
            )
        }

        composable(AppDestinations.INFORMACOES_BASICAS) {
            InformacoesBasicasScreen(
                evento = eventoAlterando,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppDestinations.INFORMACOES_ADICIONAIS) }
            )
        }

        composable(AppDestinations.INFORMACOES_ADICIONAIS) {
            InformacoesAdicionaisScreen(
                evento = eventoAlterando,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppDestinations.FATURAMENTO) }
            )
        }

        composable(AppDestinations.FATURAMENTO) {
            FaturamentoScreen(
                evento = eventoAlterando?.faturamento,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppDestinations.BANNER_EVENTO) }
            )
        }

        composable(AppDestinations.BANNER_EVENTO) {
            BannerScreen(
                evento = eventoAlterando,
                onBack = { navController.popBackStack() },
                onComplete = {
                    eventoAlterando = null
                    navController.navigate(AppDestinations.LISTAR_EVENTOS) {
                        popUpTo(AppDestinations.LISTAR_EVENTOS) { inclusive = false }
                    }
                }
            )
        }
    }
}