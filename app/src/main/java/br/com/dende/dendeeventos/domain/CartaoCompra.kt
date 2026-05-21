package br.com.dende.dendeeventos.domain

import java.time.LocalDate
import java.util.UUID

data class Cartao(
    val id: String = UUID.randomUUID().toString(),
    val usuarioId: String,
    val nomeTitular: String,
    val numero: String,
    val validade: LocalDate,
    val cvv: Int,
    val salvarParaOutrosPagamentos: Boolean = false
)
