package br.com.dende.dendeeventos.domain

import java.time.LocalDateTime

data class DadosEvento(
    val eventoId: Int,
    var statusEvento: Boolean,
    // val organizador: Usuario,
    var nome: String,
    var pagina: String,
    var descricao: String,
    var dataInicio: LocalDateTime,
    var dataFim: LocalDateTime,
    var tipoEvento: TipoEvento,
    var eventoPrincipal: DadosEvento?,
    var modalidadeEvento: ModalidadeEvento,
    var capacidadeMaxima: Int,
    var local: String,
    var preco: Double,
    var aceitaEstorno: Boolean,
    var taxaEstorno: Double,
    var urlImagem: String?
)

// Único data class utilizado por cadastrar evento, alterar evento e listar eventos do organizador.