import java.util.UUID


enum class StatusPagamento {
    PENDENTE, CONFIRMADO, RECUSADO, CANCELADO
}

data class Pagamento(
    val id: String = UUID.randomUUID().toString(),
    val ingresso: Ingresso,
    val eventoId: String,
    val cartaoId: String? = null,
    val tipoPagamento: TipoPagamento,
    val quantidade: Int,
    val precoUnitario: Double,
    val valorTotal: Double,
    var status: StatusPagamento = StatusPagamento.PENDENTE,
    val dataHora: LocalDateTime
)
