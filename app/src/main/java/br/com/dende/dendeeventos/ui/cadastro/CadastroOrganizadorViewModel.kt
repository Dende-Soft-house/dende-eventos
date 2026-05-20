package br.com.dende.dendeeventos.ui.cadastro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CadastroOrganizadorViewModel : ViewModel () {
    private val _uiState = MutableStateFlow(CadastroUiState())

    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    fun avancarPasso() {
        _uiState.update { currentState ->
            currentState.copy(currentStep = currentState.currentStep + 1)
        }
    }

    fun voltarPasso() {
        _uiState.update { currentState ->
            if (currentState.currentStep > 1) {
                currentState.copy(currentStep = currentState.currentStep - 1)
            } else {
            currentState.copy(tipoUsuario = null, isEmpresa = null)
        }
        }
    }
    fun updateTipoUsuario(novoTipo: String?) {
        _uiState.update { it.copy(tipoUsuario = novoTipo) }
    }

    fun updateIsEmpresa(ehEmpresa: Boolean?) {
        _uiState.update { it.copy(isEmpresa = ehEmpresa) }
    }

    fun updateCnpj(novoCnpj: String) {
        _uiState.update { it.copy(cnpj = novoCnpj, cnpjError = null) }
    }

    fun updateRazaoSocial(novaRazao: String) {
        _uiState.update { it.copy(razaoSocial = novaRazao, razaoSocialError = null) }
    }

    fun updateNomeFantasia(novoNomeFantasia: String) {
        _uiState.update { it.copy(nomeFantasia = novoNomeFantasia, nomeFantasiaError = null) }
    }

    fun updateEmail(novoEmail: String) {
        _uiState.update { it.copy(email = novoEmail, emailError = null) }
    }

    fun updateSenha(novaSenha: String) {
        _uiState.update { it.copy(senha = novaSenha, senhaError = null) }
    }

    fun updateNome(novoNome: String) {
        _uiState.update { it.copy(nome = novoNome, nomeError = null) }
    }

    fun updateGenero(novoGenero: String) {
        _uiState.update { it.copy(genero = novoGenero) } // Gênero não possui validação de erro complexa por ser Dropdown
    }

    fun updateDataNascimento(novaData: String) {
        _uiState.update { it.copy(dataNascimento = novaData) } // A validação de idade será feita ao clicar em Avançar
    }

    fun updateAceitouTermos(aceitou: Boolean) {
        _uiState.update { it.copy(aceitouTermos = aceitou, aceitouTermosError = false) }
    }

    // Métodos para controlar a exibição/fechamento dos Pop-ups
    fun fecharDialogErro() {
        _uiState.update { it.copy(erroAtualDialog = null) }
    }

    fun abrirDialogSucesso() {
        _uiState.update { it.copy(showSuccessDialog = true) }
    }

    fun fecharDialogSucesso() {
        _uiState.update { it.copy(showSuccessDialog = false) }
    }
}