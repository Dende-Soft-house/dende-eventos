package br.com.dende.dendeeventos.ui.cadastro

enum class TipoErroDialog {
    EMAIL_DUPLICADO,
    IDADE_MINIMA,
    CAMPOS_VAZIOS
}
data class CadastroUiState(
    val tipoUsuario: String? = null,
    val currentStep: Int = 1,
    val isEmpresa: Boolean? = null,


    val cnpj: String = "",
    val razaoSocial: String = "",
    val nomeFantasia: String = "",
    val email: String = "",
    val senha: String = "",
    val nome: String = "",
    val genero: String = "",
    val dataNascimento: String = "",
    val aceitouTermos: Boolean = false,

    // campos de erro
    val cnpjError: String? = null,
    val razaoSocialError: String? = null,
    val nomeFantasiaError: String? = null,
    val emailError: String? = null,
    val senhaError: String? = null,
    val nomeError: String? = null,
    val generoError: String? = null,
    val dataNascimentoError: String? = null,
    val aceitouTermosError: Boolean = false,

    // Controle dos Pop-ups (Modais)
    val showSuccessDialog: Boolean = false,
    val erroAtualDialog: TipoErroDialog? = null,
) {
    val totalSteps: Int
        get() = if (isEmpresa == false) 2 else 3
}