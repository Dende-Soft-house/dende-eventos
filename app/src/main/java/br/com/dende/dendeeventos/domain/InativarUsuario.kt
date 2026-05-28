package br.com.dende.dendeeventos.domain

data class InativarUsuario(
    val usuario: Usuario
    var ativo: Boolean = true
    )