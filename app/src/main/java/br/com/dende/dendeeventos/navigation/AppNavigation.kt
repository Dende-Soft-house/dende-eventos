package br.com.dende.dendeeventos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.INGRESSO
    ) {
        composable(Routes.CHECKOUT) {
            CheckoutScreen(onContinuar = { navController.navigate(Routes.PAGAMENTO) })
        }
        composable(Routes.PAGAMENTO) {
            PagamentoScreen(
                onAdicionarCartao = { navController.navigate(Routes.CADASTRO_CARTAO) },
                onConfirmar = { navController.navigate(Routes.INGRESSO) }
            )
        }
        composable(Routes.CADASTRO_CARTAO) {
            CadastroCartaoScreen(onSalvar = { navController.popBackStack() }, onFechar = { navController.popBackStack() })
        }
        composable(Routes.INGRESSO) {
            IngressoScreen(onCancelar = { navController.navigate(Routes.POLICY_CANCEL) })
        }
        composable(Routes.POLICY_CANCEL) {
            PolicyCancelScreen(
                onContinuar = { navController.navigate(Routes.CANCEL_REASON) },
                onManterIngresso = { navController.popBackStack() }
            )
        }
        composable(Routes.CANCEL_REASON) {
            CancelReasonScreen(
                onContinuar = { navController.navigate(Routes.REFUND_RESUME) },
                onVoltar = { navController.popBackStack() }
            )
        }
        composable(Routes.REFUND_RESUME) {
            RefundResumeScreen(
                onConfirmarCancelamento = { navController.navigate(Routes.CONFIRM_MODAL) },
                onVoltar = { navController.popBackStack() }
            )
        }
        composable(Routes.CONFIRM_MODAL) {
            ConfirmModalScreen(
                onContinuar = { navController.navigate(Routes.CONFIRM_CANCEL) },
                onManterIngresso = {
                    navController.popBackStack(route = Routes.INGRESSO, inclusive = false)
                }
            )
        }
        composable(Routes.CONFIRM_CANCEL) {
            ConfirmCancelScreen(
                onVoltarInicio = {
                    navController.popBackStack(route = Routes.INGRESSO, inclusive = false)
                }
            )
        }
    }
}
