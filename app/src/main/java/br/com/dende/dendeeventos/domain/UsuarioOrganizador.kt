package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class UsuarioOrganizador(
    var nome: String,
    var caminhoFotoPerfil: String? = null,
    var dataNascimento: LocalDate,
    var genero: Genero,
    val email: String,
    var senha: String,
    var cnpj: String? = null,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null,
    var ativo: Boolean = true
)
