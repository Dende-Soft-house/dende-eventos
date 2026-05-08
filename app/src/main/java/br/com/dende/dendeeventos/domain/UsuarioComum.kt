package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class Usuario(
    open var nome: String,
    open val email: String,
    open var senha: String,
    open var genero: Genero,
    open var dataNascimento: LocalDate,
    open var ativo: Boolean = true,
)
