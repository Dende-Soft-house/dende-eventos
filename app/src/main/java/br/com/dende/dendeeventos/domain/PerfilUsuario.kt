package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilUsuario(
    override var nome: String,
    override var imagemURL: String,
    override var dataNascimento: LocalDate,
    override var genero: Genero,
    override var email: String
) : Perfil()
