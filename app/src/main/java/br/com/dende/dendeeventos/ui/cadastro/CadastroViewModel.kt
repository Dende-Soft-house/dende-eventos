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

    private val _uiState = MutableStateFlow<CadastroUiState>(CadastroUiState.SelecaoPerfil)
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    // ==========================================
    // 1. NAVEGAÇÃO E SELEÇÃO
    // ==========================================
    fun selecionarPerfilUsuario() { _uiState.value = CadastroUiState.CadastroUsuarioUiState() }
    fun selecionarPerfilOrganizador() { _uiState.value = CadastroUiState.CadastroOrganizadorUiState() }

    fun voltarPasso() {
        _uiState.update { currentState ->
            when (currentState) {
                is CadastroUiState.SelecaoPerfil -> currentState
                is CadastroUiState.CadastroUsuarioUiState -> {
                    if (currentState.currentStep > 1) currentState.copy(currentStep = currentState.currentStep - 1)
                    else CadastroUiState.SelecaoPerfil
                }
                is CadastroUiState.CadastroOrganizadorUiState -> {
                    if (currentState.currentStep > 1) currentState.copy(currentStep = currentState.currentStep - 1)
                    else CadastroUiState.SelecaoPerfil
                }
            }
        }
    }

    fun avancarPasso() {
        val currentState = _uiState.value

        when (currentState) {
            is CadastroUiState.CadastroUsuarioUiState -> {
                if (currentState.currentStep == 1) {
                    if (validarDadosPessoaisGeral(currentState.nome, currentState.email, currentState.senha, currentState.dataNascimento, currentState.aceitouTermos)) {
                        _uiState.update { currentState.copy(currentStep = 2) }
                    }
                }
            }
            is CadastroUiState.CadastroOrganizadorUiState -> {
                when (currentState.currentStep) {
                    1 -> _uiState.update { currentState.copy(currentStep = 2) }
                    2 -> {
                        if (currentState.isEmpresa == true) {
                            if (currentState.cnpj.isBlank() || currentState.razaoSocial.isBlank() || currentState.nomeFantasia.isBlank()) {
                                abrirDialogErro(TipoErroDialog.CAMPOS_VAZIOS)
                            } else {
                                _uiState.update { currentState.copy(currentStep = 3) }
                            }
                        } else {
                            if (validarDadosPessoaisGeral(currentState.nome, currentState.email, currentState.senha, currentState.dataNascimento, currentState.aceitouTermos)) {
                                _uiState.update { currentState.copy(currentStep = 3) }
                            }
                        }
                    }
                    3 -> {
                        if (currentState.isEmpresa == true) {
                            if (validarDadosPessoaisGeral(currentState.nome, currentState.email, currentState.senha, currentState.dataNascimento, currentState.aceitouTermos)) {
                                _uiState.update { currentState.copy(currentStep = 4) }
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }

    // ==========================================
    // 2. FUNÇÕES DE ATUALIZAÇÃO (Digitando no Teclado)
    // ==========================================
    fun updateNome(valor: String) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(nome = valor, nomeError = null)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(nome = valor, nomeError = null)
                else -> it
            }
        }
    }

    fun updateEmail(valor: String) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(email = valor, emailError = null)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(email = valor, emailError = null)
                else -> it
            }
        }
    }

    fun updateSenha(valor: String) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(senha = valor, senhaError = null)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(senha = valor, senhaError = null)
                else -> it
            }
        }
    }

    fun updateGenero(valor: String) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(genero = valor)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(genero = valor)
                else -> it
            }
        }
    }

    fun updateDataNascimento(valor: String) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(dataNascimento = valor)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(dataNascimento = valor)
                else -> it
            }
        }
    }

    fun updateAceitouTermos(valor: Boolean) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(aceitouTermos = valor, aceitouTermosError = false)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(aceitouTermos = valor, aceitouTermosError = false)
                else -> it
            }
        }
    }

    // Apenas Organizador
    fun updateIsEmpresa(valor: Boolean) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(isEmpresa = valor) else it }
    }
    fun updateCnpj(valor: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(cnpj = valor) else it }
    }
    fun updateRazaoSocial(valor: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(razaoSocial = valor) else it }
    }
    fun updateNomeFantasia(valor: String) {
        _uiState.update { if (it is CadastroUiState.CadastroOrganizadorUiState) it.copy(nomeFantasia = valor) else it }
    }

    // ==========================================
    // 3. REGRAS DE NEGÓCIO E VALIDAÇÕES
    // ==========================================
    private fun validarDadosPessoaisGeral(nome: String, email: String, senha: String, dataNascimento: String, aceitouTermos: Boolean): Boolean {
        var temErro = false
        var emailErr: String? = null
        var senhaErr: String? = null

        if (email.isBlank()) { emailErr = "Obrigatório"; temErro = true }
        else if (!email.contains("@")) { emailErr = "E-mail inválido"; temErro = true }

        if (senha.isBlank()) { senhaErr = "Obrigatória"; temErro = true }
        else if (senha.length < 8) { senhaErr = "Mínimo 8 caracteres"; temErro = true }

        if (temErro) {
            _uiState.update {
                when (it) {
                    is CadastroUiState.CadastroUsuarioUiState -> it.copy(emailError = emailErr, senhaError = senhaErr, erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS)
                    is CadastroUiState.CadastroOrganizadorUiState -> it.copy(emailError = emailErr, senhaError = senhaErr, erroAtualDialog = TipoErroDialog.CAMPOS_VAZIOS)
                    else -> it
                }
            }
            return false
        }

        if (nome.isBlank() || dataNascimento.isBlank() || !aceitouTermos) {
            abrirDialogErro(TipoErroDialog.CAMPOS_VAZIOS)
            return false
        }

        if (!isMaiorDeIdade(dataNascimento)) {
            abrirDialogErro(TipoErroDialog.IDADE_MINIMA)
            return false
        }

        if (email.lowercase() == "teste@dende.com") {
            abrirDialogErro(TipoErroDialog.EMAIL_DUPLICADO)
            return false
        }

        return true
    }

    fun abrirDialogSucesso() {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(showSuccessDialog = true)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(showSuccessDialog = true)
                else -> it
            }
        }
    }

    fun fecharDialogSucesso() {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(showSuccessDialog = false)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(showSuccessDialog = false)
                else -> it
            }
        }
    }

    private fun abrirDialogErro(tipo: TipoErroDialog) {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(erroAtualDialog = tipo)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(erroAtualDialog = tipo)
                else -> it
            }
        }
    }

    fun fecharDialogErro() {
        _uiState.update {
            when (it) {
                is CadastroUiState.CadastroUsuarioUiState -> it.copy(erroAtualDialog = null)
                is CadastroUiState.CadastroOrganizadorUiState -> it.copy(erroAtualDialog = null)
                else -> it
            }
        }
    }

    private fun isMaiorDeIdade(dataString: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val dataNasc = sdf.parse(dataString) ?: return false
            val calNasc = Calendar.getInstance().apply { time = dataNasc }
            val calHoje = Calendar.getInstance()
            var idade = calHoje.get(Calendar.YEAR) - calNasc.get(Calendar.YEAR)
            if (calHoje.get(Calendar.DAY_OF_YEAR) < calNasc.get(Calendar.DAY_OF_YEAR)) idade--
            idade >= 18
        } catch (e: Exception) { false }
    }
}