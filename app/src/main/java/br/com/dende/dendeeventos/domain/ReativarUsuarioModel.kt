package main.java.br.com.dende.dendeeventos.domain

data class ReativarUsuarioModel(
    var email: String,
    var senha: String,
    var ativo: Boolean = false
)