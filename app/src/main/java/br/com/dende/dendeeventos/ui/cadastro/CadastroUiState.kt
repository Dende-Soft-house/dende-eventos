package br.com.dende.dendeeventos.ui.cadastro

enum class TipoErroDialog {
    EMAIL_DUPLICADO,
    IDADE_MINIMA,
    CAMPOS_VAZIOS
}

sealed interface CadastroUiState {

    data object SelecaoPerfil : CadastroUiState

    data class CadastroUsuarioUiState(
        val currentStep: Int = 1,

        // Dados Pessoais
        val nome: String = "",
        val email: String = "",
        val senha: String = "",
        val genero: String = "",
        val dataNascimento: String = "",
        val aceitouTermos: Boolean = false,

        val nomeError: String? = null,
        val emailError: String? = null,
        val senhaError: String? = null,
        val generoError: String? = null,
        val dataNascimentoError: String? = null,
        val aceitouTermosError: Boolean = false,

        // Pop-ups
        val showSuccessDialog: Boolean = false,
        val erroAtualDialog: TipoErroDialog? = null
    ) : CadastroUiState {
        val totalSteps: Int = 2
    }

    data class CadastroOrganizadorUiState(
        val currentStep: Int = 1,
        val isEmpresa: Boolean? = null,

        // Dados da Empresa
        val cnpj: String = "",
        val razaoSocial: String = "",
        val nomeFantasia: String = "",

        // Dados Pessoais do Organizador
        val nome: String = "",
        val email: String = "",
        val senha: String = "",
        val genero: String = "",
        val dataNascimento: String = "",
        val aceitouTermos: Boolean = false,

        // Campos de Erro
        val cnpjError: String? = null,
        val razaoSocialError: String? = null,
        val nomeFantasiaError: String? = null,
        val nomeError: String? = null,
        val emailError: String? = null,
        val senhaError: String? = null,
        val generoError: String? = null,
        val dataNascimentoError: String? = null,
        // ... (resto dos campos) ...
        val aceitouTermosError: Boolean = false,

        // Pop-ups
        val showSuccessDialog: Boolean = false,
        val erroAtualDialog: TipoErroDialog? = null
    ) : CadastroUiState {
        val totalSteps: Int
            get() = if (isEmpresa == false) 2 else 3
    }
}