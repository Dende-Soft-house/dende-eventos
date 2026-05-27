package br.com.dende.dendeeventos.domain

import java.time.LocalDate

sealed class Perfil {
    abstract var nome: String
    abstract var imagemURL: String
    abstract var dataNascimento: LocalDate
    abstract var genero: Genero
    abstract var email: String
}