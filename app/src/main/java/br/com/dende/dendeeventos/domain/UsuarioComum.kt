package br.com.dende.dendeeventos.domain

import java.time.LocalDate

data class UsuarioComum(
    override var nome: String,
    override val email: String,
    override var senha: String,
    override var genero: Genero,
    override var dataNascimento: LocalDate,
    override var ativo: Boolean = true
) : Usuario()
