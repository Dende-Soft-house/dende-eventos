package br.com.dende.dendeeventos.domain

import java.math.BigDecimal

data class CancelamentoIngresso(
    val ingresso: Ingresso,
    var statusCancelamento: StatusCancelamento = StatusCancelamento.NAO_INICIADO,
    var motivoCancelamento: TipoMotivoCancelamento = TipoMotivoCancelamento.OUTRO,
    val observacaoCancelamento: String? = null,
    val valorOriginal: BigDecimal,
    val valorReembolso: BigDecimal,
    val metodoPagamento: String,
)
