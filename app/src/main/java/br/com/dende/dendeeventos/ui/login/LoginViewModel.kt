package br.com.dende.dendeeventos.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dende.dendeeventos.ui.navigation.AppDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel : ViewModel() {
    private val _loginUIState = MutableStateFlow(LoginUIState())
    private val _navigationEvent = Channel<AppDestinations>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    val uiState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    fun onEmailChanged(novoEmail: String) {
        _loginUIState.update { it.copy(email = novoEmail, isEmailValid = true)}
    }

    fun onPasswordChanged(novaSenha: String) {
        _loginUIState.update { it.copy(senha = novaSenha, isPasswordValid = true) }
    }

    // Execução de login
    fun realizarLogin() {
        val emailAtual = _loginUIState.value.email
        val senhaAtual = _loginUIState.value.senha

        val emailValido = emailAtual.contains("@") && emailAtual.isNotEmpty()
        val senhaValida = senhaAtual.length >= 6

        if (!emailValido || !senhaValida) {
            _loginUIState.update {
                it.copy(isEmailValid = emailValido, isPasswordValid = senhaValida)
            }
            return
        }

        onLoginSuccess()
    }

    // Função que define o rumo após o sucesso do login
    fun onLoginSuccess() {
        viewModelScope.launch {
            // Simulando mudança de tela para a Home após o sucesso do login
        }
    }
}