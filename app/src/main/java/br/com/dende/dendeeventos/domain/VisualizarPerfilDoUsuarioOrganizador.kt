package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class VisualizarPerfilDoUsuarioOrganizador(
    var nome: String,
    var caminhoImagemPerfil: String,
    var dataNascimento: LocalDate,
    var genero: Genero,
    val email: String,
    var cnpj: String? = null,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null,
)
