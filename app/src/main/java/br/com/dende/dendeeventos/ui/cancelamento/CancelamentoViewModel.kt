package br.com.dende.dendeeventos.ui.cancelamento

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CancelamentoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CancelamentoUiState())
    val uiState: StateFlow<CancelamentoUiState> = _uiState.asStateFlow()

    fun selecionarMotivo(motivo: String) {
        _uiState.update { estadoAtual ->
            estadoAtual.copy(
                motivoSelecionado = motivo,
                erro = null
            )
        }
    }

    fun alterarObservacao(observacao: String) {
        _uiState.update { estadoAtual ->
            estadoAtual.copy(
                observacao = observacao,
                erro = null
            )
        }
    }

    fun abrirConfirmacao() {
        _uiState.update { estadoAtual ->
            estadoAtual.copy(exibirConfirmacao = true)
        }
    }

    fun fecharConfirmacao() {
        _uiState.update { estadoAtual ->
            estadoAtual.copy(exibirConfirmacao = false)
        }
    }

    fun confirmarCancelamento() {
        _uiState.update { estadoAtual ->
            estadoAtual.copy(
                exibirConfirmacao = false,
                carregando = false,
                cancelamentoConcluido = true,
                erro = null
            )
        }
    }

    fun limparErro() {
        _uiState.update { estadoAtual ->
            estadoAtual.copy(erro = null)
        }
    }
}
