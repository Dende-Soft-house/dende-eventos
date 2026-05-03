package br.com.dende.dendeeventos.domain

data class InativarUsuario(
    var ingressosUsuario: List<Ingresso>,
    val precoIngresso: Double,
    val idIngresso: BigDecimal,
    var ativo: Boolean = true
    )