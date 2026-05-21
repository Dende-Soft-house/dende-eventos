package br.com.dende.dendeeventos.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AbaIngressos {
    ATIVOS, ENCERRADOS
}

data class IngressoMock(
    val id: Int,
    val titulo: String,
    val local: String,
    val isAtivo: Boolean
)

data class ListarIngressosUiState(
    val nomeUsuario: String = "Leonardo",
    val abaSelecionada: AbaIngressos = AbaIngressos.ATIVOS,
    val ingressosExibidos: List<IngressoMock> = emptyList()
)

class ListarIngressosViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ListarIngressosUiState())

    val uiState: StateFlow<ListarIngressosUiState> = _uiState.asStateFlow()

    private val todosOsIngressos = listOf(
        IngressoMock(1, "IntegraSI FSA", "UNEX, Feira de Santana", true),
        IngressoMock(2, "DevopsDays", "Feira de Santana", true),
        IngressoMock(3, "Hackathon", "UniFTC, Feira de Santana", false)
    )

    init {
        carregarIngressos()
    }

    fun selecionarAba(aba: AbaIngressos) {
        _uiState.update { it.copy(abaSelecionada = aba) }
        carregarIngressos()
    }

    private fun carregarIngressos() {
        val abaAtual = _uiState.value.abaSelecionada
        val filtrados = todosOsIngressos.filter {
            if (abaAtual == AbaIngressos.ATIVOS) it.isAtivo else !it.isAtivo
        }

        _uiState.update { it.copy(ingressosExibidos = filtrados) }
    }
}