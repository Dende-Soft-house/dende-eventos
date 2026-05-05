package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilUsuarioComum(
    val nome: String,
    val imagemURL: String,
    val dataNascimento: LocalDate,
    val genero: Genero,
    val email: String,
)
