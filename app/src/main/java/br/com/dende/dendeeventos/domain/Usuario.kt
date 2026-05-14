package br.com.dende.dendeeventos.domain

import java.time.LocalDate

sealed class Usuario {
    abstract var nome: String
    abstract val email: String
    abstract var senha: String
    abstract var genero: Genero
    abstract var dataNascimento: LocalDate
    abstract var ativo: Boolean
}