package br.com.dende.dendeeventos.ui.fluxo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dende.dendeeventos.ui.cancelamento.CancelPolicyScreen
import br.com.dende.dendeeventos.ui.cancelamento.CancelReasonScreen
import br.com.dende.dendeeventos.ui.cancelamento.CancelamentoViewModel
import br.com.dende.dendeeventos.ui.cancelamento.CancellationSuccessScreen
import br.com.dende.dendeeventos.ui.cancelamento.RefundSummaryScreen
import br.com.dende.dendeeventos.ui.cancelamento.TicketCancelUiState
import br.com.dende.dendeeventos.ui.cancelamento.components.CancelConfirmationSheet
import br.com.dende.dendeeventos.ui.components.MetodoPagamentoScreen
import br.com.dende.dendeeventos.ui.components.QuantidadeIngressoScreen

/**
 * NavHost central do fluxo de Compra + Cancelamento.
 *
 * - O fluxo começa em [FluxoRoutes.CHECKOUT] (QuantidadeIngressoScreen).
 * - A tela "Ingresso" pertence a outro grupo e ainda não existe; quando o
 *   fluxo cair nessa rota, o NavHost pula automaticamente para a próxima
 *   tela do diagrama (Policy_Cancel) e remove Ingresso do back stack para
 *   evitar loops ao voltar.
 * - As ações "MANTER MEU INGRESSO" e "VOLTAR AO INÍCIO", que originalmente
 *   apontariam para Ingresso, foram redirecionadas para o Checkout (início
 *   real do fluxo enquanto Ingresso não existir).
 */
@Composable
fun FluxoNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = FluxoRoutes.CHECKOUT
) {
    val cancelamentoViewModel: CancelamentoViewModel = viewModel()
    val ticketState = remember { TicketCancelUiState() }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // ============================================================
        // FLUXO DE COMPRA
        // ============================================================

        // Checkout  ➜  pagamento (CONTINUAR)
        composable(route = FluxoRoutes.CHECKOUT) {
            QuantidadeIngressoScreen(
                onContinue = {
                    navController.navigate(FluxoRoutes.PAGAMENTO)
                }
            )
        }

        // pagamento  ➜  Ingresso (CONFIRMAR)  — Ingresso pula para Policy_Cancel.
        // Casdastro_Cartao é exibido como POPUP interno desta tela (AddCardPopup),
        // portanto não passa pelo NavHost.
        composable(route = FluxoRoutes.PAGAMENTO) {
            MetodoPagamentoScreen(
                onBack = { navController.popBackStack() },
                onConfirm = { navController.navigate(FluxoRoutes.INGRESSO) }
            )
        }

        // Casdastro_Cartao — mantida apenas para refletir o diagrama. A UI
        // correspondente (AddCardPopup) é exibida internamente em pagamento.
        composable(route = FluxoRoutes.CASDASTRO_CARTAO) {
            // Sem navegação ativa para esta rota.
        }

        // Ingresso — TELA DE OUTRO GRUPO (não temos).
        // Ao chegar nesta rota, pula automaticamente para a próxima tela do
        // fluxo (Policy_Cancel) e remove Ingresso do back stack para que
        // ações de "voltar" não tropecem aqui dentro.
        composable(route = FluxoRoutes.INGRESSO) {
            LaunchedEffect(Unit) {
                navController.navigate(FluxoRoutes.POLICY_CANCEL) {
                    popUpTo(FluxoRoutes.INGRESSO) { inclusive = true }
                }
            }
        }

        // ============================================================
        // FLUXO DE CANCELAMENTO
        // ============================================================

        // Policy_Cancel  ➜  Cancel_Reason  (CONTINUAR)
        // Policy_Cancel  ➜  Checkout       (MANTER MEU INGRESSO — fallback)
        composable(route = FluxoRoutes.POLICY_CANCEL) {
            CancelPolicyScreen(
                state = ticketState,
                onBackClick = { navController.popBackStack() },
                onContinueClick = {
                    navController.navigate(FluxoRoutes.CANCEL_REASON)
                },
                onKeepTicketClick = {
                    // Sem tela Ingresso, voltamos ao início do fluxo (Checkout).
                    navController.popBackStack(
                        route = FluxoRoutes.CHECKOUT,
                        inclusive = false
                    )
                }
            )
        }

        // Cancel_Reason  ➜  Refund_Resume  (CONTINUAR)
        // Cancel_Reason  ➜  Policy_Cancel  (VOLTAR)
        composable(route = FluxoRoutes.CANCEL_REASON) {
            val uiState by cancelamentoViewModel.uiState.collectAsState()
            CancelReasonScreen(
                uiState = uiState,
                onReasonSelected = cancelamentoViewModel::selecionarMotivo,
                onObservationChanged = cancelamentoViewModel::alterarObservacao,
                onContinueClick = {
                    navController.navigate(FluxoRoutes.REFUND_RESUME)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Refund_Resume  ➜  Comfirm_Modal  (CONFIRMAR CANCELAMENTO)
        // Refund_Resume  ➜  Cancel_Reason  (VOLTAR)
        composable(route = FluxoRoutes.REFUND_RESUME) {
            val uiState by cancelamentoViewModel.uiState.collectAsState()
            RefundSummaryScreen(
                state = ticketState,
                uiState = uiState,
                onConfirmClick = {
                    navController.navigate(FluxoRoutes.COMFIRM_MODAL)
                },
                onDismissConfirmation = cancelamentoViewModel::fecharConfirmacao,
                onConfirmCancellation = {
                    cancelamentoViewModel.confirmarCancelamento()
                    navController.navigate(FluxoRoutes.CONFIRM_CANCEL)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Comfirm_Modal  ➜  Confirm_Cancel (CONTINUAR após digitar "CONFIRMAR")
        // Comfirm_Modal  ➜  Checkout       (NÃO, MANTER MEU INGRESSO — fallback)
        composable(route = FluxoRoutes.COMFIRM_MODAL) {
            CancelConfirmationSheet(
                refundAmount = ticketState.refundAmount,
                onConfirm = {
                    cancelamentoViewModel.confirmarCancelamento()
                    navController.navigate(FluxoRoutes.CONFIRM_CANCEL)
                },
                onKeepTicket = {
                    navController.popBackStack(
                        route = FluxoRoutes.CHECKOUT,
                        inclusive = false
                    )
                },
                onDismiss = { navController.popBackStack() }
            )
        }

        // Confirm_Cancel  ➜  Checkout (VOLTAR AO INÍCIO — fallback)
        composable(route = FluxoRoutes.CONFIRM_CANCEL) {
            CancellationSuccessScreen(
                state = ticketState,
                onBackToHomeClick = {
                    navController.popBackStack(
                        route = FluxoRoutes.CHECKOUT,
                        inclusive = false
                    )
                }
            )
        }
    }
}