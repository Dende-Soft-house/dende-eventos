package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class Usuario(
    var nome: String,
    val email: String,
    var senha: String,
    var genero: Genero,
    var dataNascimento: LocalDate,
    var ativo: Boolean = true
)
