package main.java.br.com.dende.dendeeventos.domain

import java.time.LocalDateTime
import main.java.br.com.dende.dendeeventos.domain.Genero

data class AlterarPerfilUsuarioComumModel(
    var nome: String,
    var email: String,
    var genero: Genero,
    var caminhoFotoPerfil: String? = null,
    var dataNascimento: LocalDateTime,
    var ativo: Boolean = true
)
