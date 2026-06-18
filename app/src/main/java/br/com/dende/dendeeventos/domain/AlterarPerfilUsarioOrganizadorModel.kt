package br.com.dende.dendeeventos.domain

import java.time.LocalDateTime

data class AlterarPerfilUsuarioOrganizador (
    var nome: String,
    var caminhoUrlImagemPerfil: String?,
    val email: String,
    var nomeFantasiaCnpj: String,
    var razaoSocialCnpj: String,
    var cnpj: String,
    var dataNascimento: LocalDateTime,
    val isCnpj : Boolean
)



