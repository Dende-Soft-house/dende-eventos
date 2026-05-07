package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilUsuario(
    var nome: String,
    var imagemURL: String,
    var dataNascimento: LocalDate,
    var genero: Genero,
    var email: String,
)
