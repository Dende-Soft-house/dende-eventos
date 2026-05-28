package br.com.dende.dendeeventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.BannerScreen
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.CadastrarAlterarEventoViewModel
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.FaturamentoScreen
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.InformacoesAdicionaisScreen
import br.com.dende.dendeeventos.ui.cadastrar_alterar_evento.InformacoesBasicasScreen
import br.com.dende.dendeeventos.ui.listar_eventos_organizador.ListarEventosOrganizadorViewModel
import br.com.dende.dendeeventos.ui.listar_eventos_organizador.MeusEventosScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val cadastroViewModel: CadastrarAlterarEventoViewModel = viewModel()
    val listaViewModel: ListarEventosOrganizadorViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppDestinations.LISTAR_EVENTOS) {
        composable(AppDestinations.LISTAR_EVENTOS) {
            MeusEventosScreen(
                viewModel = listaViewModel,
                onBackClick = { navController.popBackStack() },
                onEventClick = {
                    idClicado ->
                    val eventoSelecionado = listaViewModel.uiState.value.eventos.find {
                        it.eventoId.toString() == idClicado
                    }
                    cadastroViewModel.carregarEventoParaAlterar(eventoSelecionado)
                    navController.navigate(AppDestinations.INFORMACOES_BASICAS)
                },
                onAddEventClick = {
                    cadastroViewModel.limparEstado()
                    navController.navigate(AppDestinations.INFORMACOES_BASICAS)
                }
            )
        }

        composable(AppDestinations.INFORMACOES_BASICAS) {
            InformacoesBasicasScreen(
                viewModel = cadastroViewModel,
                onBack = {
                    cadastroViewModel.limparEstado()
                    navController.popBackStack()
                         },
                onNext = { navController.navigate(AppDestinations.INFORMACOES_ADICIONAIS) }
            )
        }

        composable(AppDestinations.INFORMACOES_ADICIONAIS) {
            InformacoesAdicionaisScreen(
                viewModel = cadastroViewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppDestinations.FATURAMENTO) }
            )
        }

        composable(AppDestinations.FATURAMENTO) {
            FaturamentoScreen(
                viewModel = cadastroViewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(AppDestinations.BANNER_EVENTO) }
            )
        }

        composable(AppDestinations.BANNER_EVENTO) {
            BannerScreen(
                viewModel = cadastroViewModel,
                onBack = { navController.popBackStack() },
                onComplete = {
                    val eventoPronto = cadastroViewModel.eventoParaSalvar()
                    cadastroViewModel.limparEstado()
                    navController.navigate(AppDestinations.LISTAR_EVENTOS) {
                        popUpTo(AppDestinations.LISTAR_EVENTOS) { inclusive = false }
                    }
                }
            )
        }
    }
}