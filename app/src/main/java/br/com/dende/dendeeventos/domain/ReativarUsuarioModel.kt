package main.java.br.com.dende.dendeeventos.domain

data class ReativarUsuarioModel(
    var email: String,
    var ativo: Boolean = false
)