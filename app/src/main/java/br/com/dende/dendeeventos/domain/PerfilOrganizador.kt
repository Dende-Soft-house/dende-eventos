package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilOrganizador(
    override var nome: String,
    override var imagemURL: String,
    override var dataNascimento: LocalDate,
    override var genero: Genero,
    override var email: String,
    var dadosEmpresa: Empresa? // Campo específico do organizador
) : Perfil()