package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class Organizador(
    var nome: String,
    var caminhoFotoPerfil: String? = "",
    var dataNascimento: LocalDate,
    var genero: Genero,
    val email: String,
    var senha: String,
    var dadosEmpresa: Empresa?,
    var ativo: Boolean = true
)
