package br.com.dende.dendeeventos.ui.cadastro

data class CadastroUiState(
    val tipoUsuario: String? = null,
    val currentStep: Int = 1,
    val isEmpresa: Boolean? = null,


    val cnpj: String = "",
    val razaoSocial: String = "",
    val nomeFantasia: String = "",
    val email: String = "",
    val senha: String = "",
    val nomeProprietario: String = "",
    val genero: String = "",
    val dataNascimento: String = ""
) {
    val totalSteps: Int
        get() = if (isEmpresa == false) 2 else 3
}