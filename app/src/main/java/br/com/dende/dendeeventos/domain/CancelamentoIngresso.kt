package br.com.dende.dendeeventos.domain

data class CancelamentoIngresso(
    val idPedido: String,
    val ingresso: Ingresso,
    var statusCancelamento: StatusCancelamento.NAO_INICIADO,
    val percentualTaxaCancelamento: Int, // de 0 a 100
    val motivoCancelamento: TipoMotivoCancelamento? = null,
    val observacaoCancelamento: String? = null,
    val valorOriginal: Double,
    val taxaCancelamento: Double, // Ver Tipo (float talvez ou algo similar)
    val valorReembolso: Double,
    val metodoPagamento: String,
)