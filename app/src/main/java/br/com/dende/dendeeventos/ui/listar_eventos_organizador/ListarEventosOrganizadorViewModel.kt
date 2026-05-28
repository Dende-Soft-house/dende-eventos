package br.com.dende.dendeeventos.ui.listar_eventos_organizador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dende.dendeeventos.domain.Evento
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListarEventosOrganizadorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListarEventosOrganizadorUIState())
    val uiState: StateFlow<ListarEventosOrganizadorUIState> = _uiState.asStateFlow()

    fun carregarEventosDoOrganizador(organizadorLogado: Long) {
        _uiState.update { it.copy(isLoading = true, erroMensagem = null) }

        viewModelScope.launch {
            try {
                val todosEventos = exemploBusca()

                val eventosProcessados = todosEventos
                    // Regra 1: Apenas eventos cadastrados por ele
                    .filter { it.organizador == organizadorLogado }
                    .sortedWith(
                        compareBy<Evento> { it.dataInicio }
                            .thenBy { it.nome }
                    )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        eventos = eventosProcessados
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erroMensagem = "Não foi possível carregar os eventos. Tente novamente."
                    )
                }
            }
        }
    }

    // Função temporária para busca de eventos sem banco de dados
    private suspend fun exemploBusca(): List<Evento> {
        delay(1000)
        return emptyList()
    }
}