package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilUsuarioComum(
    var nome: String,
    val imagemURL: String,
    var dataNascimento: LocalDate,
    var genero: Genero,
    val email: String,
)
