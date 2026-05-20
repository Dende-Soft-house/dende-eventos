package br.com.dende.dendeeventos.domain

import java.time.LocalDateTime

data class Faturamento(
    val evento: Evento,
    var preco: Double,
    var aceitaEstorno: Boolean,
    var taxaEstorno: Double
)

data class Evento(
    var eventoId: Long,
    var ativo: Boolean = false,
    // val organizador: Usuario,
    var nome: String,
    var paginaWeb: String = "",
    var descricao: String,
    var dataInicio: LocalDateTime,
    var dataFim: LocalDateTime,
    var tipoEvento: TipoEvento,
    var eventoPrincipal: Evento?,
    var modalidadeEvento: ModalidadeEvento,
    var capacidadeMaxima: Int,
    var local: String,
    var faturamento: Faturamento,
    var urlBanner: String?
)

// Único data class utilizado por cadastrar evento, alterar evento e listar eventos do organizador.