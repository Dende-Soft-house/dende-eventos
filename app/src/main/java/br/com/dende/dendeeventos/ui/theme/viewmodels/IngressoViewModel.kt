package br.com.dende.dendeeventos.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Guarda todas as informações que a tela precisa mostrar
data class IngressoUiState(
    val titulo: String = "IntegraSI FSA",
    val descricao: String = "Um encontro de tecnologia, inovação e conexão",
    val dataHora: String = "21 de Abril as 18:40",
    val local: String = "UNEX, Feira de Santana - BA",
    val tipoEntrada: String = "Entrada Gratuita",
    val nomeTitular: String = "Chaira Sacra"
)

class IngressoViewModel : ViewModel() {

    // Cria o estado inicial usando os dados acima
    private val _uiState = MutableStateFlow(IngressoUiState())
    val uiState: StateFlow<IngressoUiState> = _uiState.asStateFlow()

    // Função que será chamada ao clicar em "Baixar"
    fun baixarIngresso() {
        println("Iniciando download do ingresso...")
        // Futuramente: Lógica para gerar e baixar PDF
    }

    // Função que será chamada ao clicar em "Cancelar"
    fun cancelarIngresso() {
        println("Solicitação de cancelamento de ingresso...")
        // Futuramente: Lógica para abrir modal de confirmação ou cancelar no banco
    }

    // Função para voltar para a tela anterior
    fun voltar() {
        println("Voltando para a tela de lista de ingressos...")
    }
}