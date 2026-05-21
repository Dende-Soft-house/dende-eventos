package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class PerfilUsuario(
    open var nome: String,
    open var imagemURL: String,
    open var dataNascimento: LocalDate,
    open var genero: Genero,
    open var email: String,
)
