package br.com.dende.dendeeventos.ui.login

// Classe do model Login
data class LoginUIState (
    var email: String = "",
    var senha: String = "",
    // Variáveis que lidam com verificação das credenciais
    var isEmailValid: Boolean = true,
    var isPasswordValid: Boolean = true,
    // Variável que lida com feedbacks de carregamento. Pode ser útil em situações em que o botão de login é desativado para evitar múltiplas requisições.
    var isLoading: Boolean = false,
    // Variável que lida com as mensagens de eventuais erros ao preencher os campos
    var mensagemErro: String = ""
)