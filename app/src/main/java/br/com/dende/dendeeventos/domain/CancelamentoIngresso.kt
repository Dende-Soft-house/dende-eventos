package br.com.dende.dendeeventos.domain

data class CancelamentoIngresso(
    val idPedido: String,
    val ingresso: Ingresso,
    var statusCancelamento: StatusCancelamento.NAO_INICIADO,
    val percentualTaxaCancelamento: Int, // de 0 a 100
    var motivoCancelamento: TipoMotivoCancelamento = TipoMotivoCancelamento.OUTRO,
    val observacaoCancelamento: String? = null,
    val valorOriginal: BigDecimal,
    val taxaCancelamento: BigDecimal, // Ver Tipo (float talvez ou algo similar)
    val valorReembolso: Double,
    val metodoPagamento: String,
)