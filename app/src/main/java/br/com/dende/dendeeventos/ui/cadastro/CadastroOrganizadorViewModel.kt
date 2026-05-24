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
        // Pega todos os dados que o usuário já digitou até agora
        val estadoAtual = _uiState.value

        // REGRAS DO PASSO 2 (Dados Empresariais)
        if (estadoAtual.currentStep == 2 && estadoAtual.isEmpresa == true) {

            // CNPJ não pode estar vazio
            if (estadoAtual.cnpj.isBlank()) {
                _uiState.update { it.copy(cnpjError = "O CNPJ é obrigatório.") }
                return
            }

            // CNPJ tem que ter o tamanho certo (14 números)
            // (Colocamos 18 contando com os pontos e traço da máscara: 00.000.000/0001-00)
            if (estadoAtual.cnpj.length < 18) {
                _uiState.update { it.copy(cnpjError = "CNPJ incompleto.") }
                return
            }

            // Razão Social não pode ser vazia
            if (estadoAtual.razaoSocial.isBlank()) {
                _uiState.update { it.copy(razaoSocialError = "A Razão Social é obrigatória.") }
                return
            }
        }

        // Verifica se está no passo 3 da Empresa OU no passo 2 da Pessoa Física
        if (estadoAtual.currentStep == 3 || (estadoAtual.currentStep == 2 && estadoAtual.isEmpresa == false)) {

            // E-mail tem que ter um "@"
            if (!estadoAtual.email.contains("@")) {
                _uiState.update { it.copy(emailError = "Digite um e-mail válido.") }
                return
            }

            // A senha precisa ser forte (mínimo 8 caracteres)
            if (estadoAtual.senha.length < 8) {
                _uiState.update { it.copy(senhaError = "A senha deve ter no mínimo 8 caracteres.") }
                return
            }

            // Nome não pode ser vazio
            if (estadoAtual.nome.isBlank()) {
                _uiState.update { it.copy(nomeError = "O nome é obrigatório.") }
                return
            }

            // O usuário DEVE aceitar os termos de uso
            if (estadoAtual.aceitouTermos == false) {
                _uiState.update { it.copy(aceitouTermosError = true) }
                return
            }

            if (estadoAtual.email.isBlank()) {
                _uiState.update {
                    it.copy(
                        emailError = "E-mail obrigatório",
                        erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS
                    )
                }
                return
            }
        }

        // Se o código chegou até aqui sem cair em nenhum return, os dados estão corretos e mudamos para a nova tela
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