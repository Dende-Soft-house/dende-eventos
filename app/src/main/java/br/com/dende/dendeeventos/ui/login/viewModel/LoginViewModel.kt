package br.com.dende.dendeeventos.ui.login.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUIState = MutableStateFlow(LoginUIState())
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
    }
}