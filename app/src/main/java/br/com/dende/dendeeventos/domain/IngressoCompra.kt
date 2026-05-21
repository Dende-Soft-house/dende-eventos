package br.com.dende.dendeeventos.domain

import java.util.UUID

data class Ingresso(
    val id: String = UUID.randomUUID().toString(),
    val usuario: Usuario,
    val evento: EventoStatus,
    val assento: String,
    var status: StatusIngresso = StatusIngresso.ATIVO
)
