package br.com.dende.dendeeventos.ui.cadastro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CadastroOrganizadorViewModel : ViewModel () {
    private val _uiState = MutableStateFlow(CadastroUiState())

    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    fun avancarPasso() {
        val currentState = _uiState.value

        when (currentState.currentStep) {
            1 -> {
                // É empresa? Não tem validação complexa, só avança
                _uiState.update { it.copy(currentStep = it.currentStep + 1) }
            }
            2 -> {
                if (currentState.isEmpresa == true) {
                    // Validar Formulário de Empresa
                    if (currentState.cnpj.isBlank() || currentState.razaoSocial.isBlank() || currentState.nomeFantasia.isBlank()) {
                        _uiState.update { it.copy(erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS) }
                        return
                    }
                    _uiState.update { it.copy(currentStep = it.currentStep + 1) }
                } else {
                    // Validar Formulário Dados Pessoais
                    if (validarDadosPessoais(currentState)) {
                        _uiState.update { it.copy(currentStep = it.currentStep + 1) }
                    }
                }
            }
            3 -> {
                if (currentState.isEmpresa == true) {
                    // Validar Formulário de Dados Pessoais (Para os usuarios que são empresa)
                    if (validarDadosPessoais(currentState)) {
                        _uiState.update { it.copy(currentStep = it.currentStep + 1) }
                    }
                }
            }
        }
    }

    private fun validarDadosPessoais(state: CadastroUiState): Boolean {
        var temErroDeFormato = false
        var novoEmailError: String? = null
        var novaSenhaError: String? = null

        // 1. Validação do E-mail
        if (state.email.isBlank()) {
            novoEmailError = "O e-mail é obrigatório"
            temErroDeFormato = true
        } else if (!state.email.contains("@")) { // Verifica se tem o '@'
            novoEmailError = "Digite um e-mail válido contendo '@'"
            temErroDeFormato = true
        }

        // 2. Validação da Senha
        if (state.senha.isBlank()) {
            novaSenhaError = "A senha é obrigatória"
            temErroDeFormato = true
        } else if (state.senha.length < 8) { // Verifica o tamanho mínimo
            novaSenhaError = "A senha deve ter no mínimo 8 caracteres"
            temErroDeFormato = true
        }

        // Se o e-mail ou a senha estiverem mal formatados, avisa o TextField e abre o pop-up
        if (temErroDeFormato) {
            _uiState.update {
                it.copy(
                    emailError = novoEmailError,
                    senhaError = novaSenhaError,
                    erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS
                )
            }
            return false
        } else {
            // Limpa os erros visuais caso o utilizador tenha corrigido
            _uiState.update { it.copy(emailError = null, senhaError = null) }
        }

        // 3. Os restantes campos não podem estar vazios
        if (state.nome.isBlank() || state.dataNascimento.isBlank() || !state.aceitouTermos) {
            _uiState.update { it.copy(erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS) }
            return false
        }

        // 4. Idade mínima de 18 anos
        if (!isMaiorDeIdade(state.dataNascimento)) {
            _uiState.update { it.copy(erroAtualDialog = TipoErroDialog.IDADE_MINIMA) }
            return false
        }

        // 5. Email duplicado (Simulação para testes)
        if (state.email.lowercase() == "teste@dende.com") {
            _uiState.update { it.copy(erroAtualDialog = TipoErroDialog.EMAIL_DUPLICADO) }
            return false
        }

        // Se passou por todas as regras, retorna true e libera o avanço!
        return true
    }

    // Função que calcula se a pessoa tem 18 anos hoje
    private fun isMaiorDeIdade(dataString: String): Boolean {
        return try {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            sdf.isLenient = false
            val dataNascimentoForCalculo = sdf.parse(dataString) ?: return false

            val calculoNascimento = java.util.Calendar.getInstance().apply { time = dataNascimentoForCalculo }
            val calculoHoje = java.util.Calendar.getInstance()

            var idade = calculoHoje.get(java.util.Calendar.YEAR) - calculoNascimento.get(java.util.Calendar.YEAR)

            // Subtrai 1 ano se o aniversário ainda não aconteceu este ano
            if (calculoHoje.get(java.util.Calendar.DAY_OF_YEAR) < calculoNascimento.get(java.util.Calendar.DAY_OF_YEAR)) {
                idade--
            }

            idade >= 18
        } catch (e: Exception) {
            false
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
        _uiState.update { it.copy(genero = novoGenero) }
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