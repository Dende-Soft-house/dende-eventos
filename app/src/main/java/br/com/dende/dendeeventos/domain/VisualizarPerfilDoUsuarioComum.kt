package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilUsuarioComum(
    var nome: String,
    var caminhoImagemPerfil: String,
    var dataNascimento: LocalDate,
    var genero: Genero,
    val email: String,
)
