package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class UsuarioOrganizador(
    var nome: String,
    var caminhoFotoPerfil: String? = null,
    var dataNascimento: LocalDate,
    var genero: Genero,
    val email: String,
    var senha: String,
    var dadosEmpresa: Empresa,
    var ativo: Boolean = true
)
