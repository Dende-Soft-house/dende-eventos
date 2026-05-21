package com.dendesofthouse.domain

import java.time.LocalDateTime

data class Usuario(
    val id: Long,
    val nome: String,
    val email: String,
    var status: StatusUsuario,
    var reativadoEm: LocalDateTime? = null
)

enum StatusUsuario {
    ATIVO, INATIVO
}
