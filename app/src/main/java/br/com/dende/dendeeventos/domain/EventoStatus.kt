package br.com.dende.dendeeventos.domain

enum class EtapaDesativacaoEvento {
    AGUARDANDO_CONFIRMACAO, SUCESSO, ERRO
}

data class EventoStatus(
    val eventoId: Int,
    var nomeEvento: String,
    var ativo: Boolean = false,
    var statusOperacao: EtapaDesativacaoEvento = EtapaDesativacaoEvento.AGUARDANDO_CONFIRMACAO
)
