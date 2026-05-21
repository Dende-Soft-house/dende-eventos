package com.dendeeventos.softhouse.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// =======================================================
// 1. ESTADOS DA TELA (STATE)
// =======================================================
sealed interface ReativacaoUiState {
    object Ocioso : ReativacaoUiState
    object Carregando : ReativacaoUiState
    data class Sucesso(val mensagem: String) : ReativacaoUiState
    data class Erro(val erroMensagem: String) : ReativacaoUiState
}

// =======================================================
// 2. CÉREBRO DA LOGICA (VIEWMODEL + FUNÇÃO)
// =======================================================
class UsuarioViewModel : ViewModel() {

    // Gerencia o estado atual e expõe apenas para leitura da interface gráfica
    var uiState: ReativacaoUiState by mutableStateOf(ReativacaoUiState.Ocioso)
        private set

    // A função principal de reativação solicitada
    fun reativarContaDoUsuario(usuarioId: Long) {
        viewModelScope.launch {
            // Define o estado atual como carregamento
            uiState = ReativacaoUiState.Carregando

            try {
                // Simula a latência de rede com o servidor da Soft House (2 segundos)
                delay(2000)

                // Regra de validação cadastral
                if (usuarioId <= 0) {
                    throw Exception("ID inválido. Cadastro não localizado no sistema Dendê Eventos.")
                }

                // Altera o estado para Sucesso se a operação for concluída
                uiState = ReativacaoUiState.Sucesso("Sua conta no Dendê Eventos foi reativada com sucesso!")
                
            } catch (exception: Exception) {
                // Captura falhas (ex: falta de internet, erro de servidor) e expõe o erro
                uiState = ReativacaoUiState.Erro(exception.message ?: "Erro desconhecido no servidor.")
            }
        }
    }

    // Função auxiliar para redefinir o fluxo se o usuário quiser tentar de novo
    fun resetarEstado() {
        uiState = ReativacaoUiState.Ocioso
    }
}
