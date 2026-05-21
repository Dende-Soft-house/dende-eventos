package br.com.dende.dendeeventos.domain

import java.time.LocalDateTime
import java.util.UUID

data class Pagamento(
    val id: String = UUID.randomUUID().toString(),
    val ingressoId: String,
    val eventoId: Int,
    val cartaoId: String? = null,
    val tipoPagamento: TipoPagamento,
    val quantidade: Int,
    val precoUnitario: Double,
    val valorTotal: Double,
    var status: StatusPagamento = StatusPagamento.PENDENTE,
    val dataHora: LocalDateTime
)
