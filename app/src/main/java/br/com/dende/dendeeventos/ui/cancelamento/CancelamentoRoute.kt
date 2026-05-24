package br.com.dende.dendeeventos.ui.cancelamento

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CancelamentoRoute(
    viewModel: CancelamentoViewModel,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    CancelReasonScreen(
        uiState = uiState,
        onReasonSelected = viewModel::selecionarMotivo,
        onObservationChanged = viewModel::alterarObservacao,
        onContinueClick = viewModel::abrirConfirmacao,
        onBackClick = onBackClick
    )
}