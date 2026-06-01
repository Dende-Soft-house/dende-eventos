package br.com.dende.dendeeventos.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class IngressoUiState(
    val titulo: String = "IntegraSI FSA",
    val descricao: String = "Um encontro de tecnologia, inovação e conexão",
    val dataHora: String = "21 de Abril as 18:40",
    val local: String = "UNEX, Feira de Santana - BA",
    val tipoEntrada: String = "Entrada Gratuita",
    val nomeTitular: String = "Chaira Sacra"
)

class IngressoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(IngressoUiState())
    val uiState: StateFlow<IngressoUiState> = _uiState.asStateFlow()

    fun baixarIngresso() {
        println("Iniciando download do ingresso...")
    }

    fun cancelarIngresso() {
        println("Solicitação de cancelamento de ingresso...")
    }

    fun voltar() {
        println("Voltando para a tela de lista de ingressos...")
    }
}