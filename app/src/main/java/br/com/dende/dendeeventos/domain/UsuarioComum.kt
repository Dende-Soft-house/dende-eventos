package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class UsuarioComum(
    var nome: String,
    val email: String,
    var senha: String,
    var genero: Genero,
    var dataNascimento: LocalDate,
    var caminhoFotoPerfil: String?,
    var ativo: Boolean = true,
)
