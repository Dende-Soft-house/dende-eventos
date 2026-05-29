package br.com.dende.dendeeventos.ui.cadastro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CadastroViewModel : ViewModel() {

    // Começamos na tela inicial de seleção de perfil
    private val _uiState = MutableStateFlow<CadastroUiState>(CadastroUiState.SelecaoPerfil)
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    // ==========================================
    // 1. NAVEGAÇÃO GERAL E SELEÇÃO DE PERFIL
    // ==========================================

    fun selecionarPerfilUsuario() {
        _uiState.value = CadastroUiState.CadastroUsuarioUiState()
    }

    fun selecionarPerfilOrganizador() {
        _uiState.value = CadastroUiState.CadastroOrganizadorUiState()
    }

    fun voltarPasso() {
        _uiState.update { currentState ->
            when (currentState) {
                is CadastroUiState.SelecaoPerfil -> currentState

                is CadastroUiState.CadastroUsuarioUiState -> {
                    if (currentState.currentStep > 1) {
                        currentState.copy(currentStep = currentState.currentStep - 1)
                    } else CadastroUiState.SelecaoPerfil
                }

                is CadastroUiState.CadastroOrganizadorUiState -> {
                    if (currentState.currentStep > 1) {
                        currentState.copy(currentStep = currentState.currentStep - 1)
                    } else CadastroUiState.SelecaoPerfil
                }
            }
        }
    }

    fun avancarPasso() {
        val currentState = _uiState.value

        when (currentState) {
            is CadastroUiState.SelecaoPerfil -> { /* Botões tratam isso */ }
            is CadastroUiState.CadastroUsuarioUiState -> processarAvancoUsuario(currentState)
            is CadastroUiState.CadastroOrganizadorUiState -> processarAvancoOrganizador(currentState)
        }
    }

    // ==========================================
    // 2. LÓGICA DO USUÁRIO (O SEU FLUXO)
    // ==========================================

    private fun processarAvancoUsuario(state: CadastroUiState.CadastroUsuarioUiState) {
        if (state.currentStep == 1) {
            if (validarDadosUsuario(state)) {
                _uiState.update { state.copy(currentStep = 2) }
            }
        } else if (state.currentStep == 2) {
            abrirDialogSucesso()
        }
    }

    private fun validarDadosUsuario(state: CadastroUiState.CadastroUsuarioUiState): Boolean {
        if (state.nome.isBlank() || state.dataNascimento.isBlank() || !state.aceitouTermos) {
            _uiState.update { state.copy(erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS) }
            return false
        }
        if (!isMaiorDeIdade(state.dataNascimento)) {
            _uiState.update { state.copy(erroAtualDialog = TipoErroDialog.IDADE_MINIMA) }
            return false
        }
        return true
    }

    fun updateNomeUsuario(novoNome: String) {
        _uiState.update { if (it is CadastroUiState.CadastroUsuarioUiState) it.copy(nome = novoNome, nomeError = null) else it }
    }

    fun updateEmailUsuario(novoEmail: String) {
        _uiState.update { if (it is CadastroUiState.CadastroUsuarioUiState) it.copy(email = novoEmail, emailError = null) else it }
    }

    fun updateSenhaUsuario(novaSenha: String) {
        _uiState.update { if (it is CadastroUiState.CadastroUsuarioUiState) it.copy(senha = novaSenha, senhaError = null) else it }
    }

    fun updateDataNascimentoUsuario(novaData: String) {
        _uiState.update { if (it is CadastroUiState.CadastroUsuarioUiState) it.copy(dataNascimento = novaData, dataNascimentoError = null) else it }
    }

    fun updateAceiteTermosUsuario(aceite: Boolean) {
        _uiState.update { if (it is CadastroUiState.CadastroUsuarioUiState) it.copy(aceitouTermos = aceite) else it }
    }

    // ==========================================
    // 3. LÓGICA DO ORGANIZADOR (O FLUXO DO SEU COLEGA)
    // ==========================================

    private fun processarAvancoOrganizador(state: CadastroUiState.CadastroOrganizadorUiState) {
        when (state.currentStep) {
            1 -> {
                // Passo 1: Definiu se é empresa ou não. Apenas avança.
                if (state.isEmpresa != null) {
                    _uiState.update { state.copy(currentStep = 2) }
                }
            }
            2 -> {
                if (state.isEmpresa == true) {
                    // Validar Formulário de Empresa (CNPJ, etc)
                    if (state.cnpj.isBlank() || state.razaoSocial.isBlank() || state.nomeFantasia.isBlank()) {
                        _uiState.update { state.copy(erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS) }
                        return
                    }
                    _uiState.update { state.copy(currentStep = 3) }
                } else {
                    // Validar Formulário Dados Pessoais (Para quem NÃO é empresa)
                    if (validarDadosPessoaisOrganizador(state)) {
                        _uiState.update { state.copy(currentStep = 3) }
                    }
                }
            }
            3 -> {
                if (state.isEmpresa == true) {
                    // Validar Formulário Dados Pessoais (Para quem É empresa)
                    if (validarDadosPessoaisOrganizador(state)) {
                        _uiState.update { state.copy(currentStep = 4) }
                    }
                } else {
                    // Passo 3 para quem NÃO é empresa é a tela de Confirmação Final
                    abrirDialogSucesso()
                }
            }
            4 -> {
                // Passo 4 para quem É empresa é a tela de Confirmação Final
                abrirDialogSucesso()
            }
        }
    }

    // A LÓGICA DE VALIDAÇÃO DO SEU COLEGA APLICADA AQUI
    private fun validarDadosPessoaisOrganizador(state: CadastroUiState.CadastroOrganizadorUiState): Boolean {
        var temErroDeFormato = false
        var novoEmailError: String? = null
        var novaSenhaError: String? = null

        // 1. Validação do E-mail
        if (state.email.isBlank()) {
            novoEmailError = "O e-mail é obrigatório"
            temErroDeFormato = true
        } else if (!state.email.contains("@")) {
            novoEmailError = "Digite um e-mail válido contendo '@'"
            temErroDeFormato = true
        }

        // 2. Validação da Senha
        if (state.senha.isBlank()) {
            novaSenhaError = "A senha é obrigatória"
            temErroDeFormato = true
        } else if (state.senha.length < 8) {
            novaSenhaError = "A senha deve ter no mínimo 8 caracteres"
            temErroDeFormato = true
        }

        if (temErroDeFormato) {
            _uiState.update {
                if (it is CadastroUiState.CadastroOrganizadorUiState) {
                    it.copy(emailError = novoEmailError, senhaError = novaSenhaError, erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS)
                } else it
            }
            return false
        } else {
            _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(emailError = null, senhaError = null) else it }
        }

        // 3. Campos vazios e termos
        if (state.nome.isBlank() || state.dataNascimento.isBlank() || !state.aceitouTermos) {
            _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS) else it }
            return false
        }

        // 4. Idade mínima
        if (!isMaiorDeIdade(state.dataNascimento)) {
            _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(erroAtualDialog = TipoErroDialog.IDADE_MINIMA) else it }
            return false
        }

        // 5. Email duplicado
        if (state.email.lowercase() == "teste@dende.com") {
            _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(erroAtualDialog = TipoErroDialog.EMAIL_DUPLICADO) else it }
            return false
        }

        return true
    }

    // Funções de Update do Organizador
    fun updateIsEmpresaOrganizador(ehEmpresa: Boolean) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(isEmpresa = ehEmpresa) else it }
    }

    fun updateCnpjOrganizador(novoCnpj: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(cnpj = novoCnpj, cnpjError = null) else it }
    }

    fun updateRazaoSocialOrganizador(novaRazao: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(razaoSocial = novaRazao, razaoSocialError = null) else it }
    }

    fun updateNomeFantasiaOrganizador(novoNomeFantasia: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(nomeFantasia = novoNomeFantasia, nomeFantasiaError = null) else it }
    }

    fun updateEmailOrganizador(novoEmail: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(email = novoEmail, emailError = null) else it }
    }

    fun updateSenhaOrganizador(novaSenha: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(senha = novaSenha, senhaError = null) else it }
    }

    fun updateNomeOrganizador(novoNome: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(nome = novoNome, nomeError = null) else it }
    }

    fun updateGeneroOrganizador(novoGenero: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(genero = novoGenero, generoError = null) else it }
    }

    fun updateDataNascimentoOrganizador(novaData: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(dataNascimento = novaData, dataNascimentoError = null) else it }
    }

    fun updateAceiteTermosOrganizador(aceitou: Boolean) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(aceitouTermos = aceitou, aceitouTermosError = false) else it }
    }

    // ==========================================
    // 4. MÉTODOS UTILITÁRIOS E DIALOGS
    // ==========================================

    fun fecharDialogErro() {
        _uiState.update { currentState ->
            when (currentState) {
                is CadastroUiState.CadastroUsuarioUiState -> currentState.copy(erroAtualDialog = null)
                is CadastroUiState.CadastroOrganizadorUiState -> currentState.copy(erroAtualDialog = null)
                is CadastroUiState.SelecaoPerfil -> currentState
            }
        }
    }

    fun abrirDialogSucesso() {
        _uiState.update { currentState ->
            when (currentState) {
                is CadastroUiState.CadastroUsuarioUiState -> currentState.copy(showSuccessDialog = true)
                is CadastroUiState.CadastroOrganizadorUiState -> currentState.copy(showSuccessDialog = true)
                is CadastroUiState.SelecaoPerfil -> currentState
            }
        }
    }

    fun fecharDialogSucesso() {
        _uiState.update { currentState ->
            when (currentState) {
                is CadastroUiState.CadastroUsuarioUiState -> currentState.copy(showSuccessDialog = false)
                is CadastroUiState.CadastroOrganizadorUiState -> currentState.copy(showSuccessDialog = false)
                is CadastroUiState.SelecaoPerfil -> currentState
            }
        }
    }

    private fun isMaiorDeIdade(dataString: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val dataNascimentoForCalculo = sdf.parse(dataString) ?: return false

            val calculoNascimento = Calendar.getInstance().apply { time = dataNascimentoForCalculo }
            val calculoHoje = Calendar.getInstance()

            var idade = calculoHoje.get(Calendar.YEAR) - calculoNascimento.get(Calendar.YEAR)

            if (calculoHoje.get(Calendar.DAY_OF_YEAR) < calculoNascimento.get(Calendar.DAY_OF_YEAR)) {
                idade--
            }
            idade >= 18
        } catch (e: Exception) {
            false
        }
    }
}