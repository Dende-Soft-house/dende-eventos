// Enum Class para tela de ativação e desativação de evento
enum class EtapaDesativacaoEvento {
    AGUARDANDO_CONFIRMACAO, SUCESSO, ERRO
}

data class EventoStatus(
    val evento: Evento,
    var nomeEvento: String,
    // Variável responsável por controlar o estado do evento (ativo/inativo)
    var ativo: Boolean = false,
    var statusOperacao: EtapaDesativacaoEvento = EtapaDesativacaoEvento.AGUARDANDO_CONFIRMACAO
)
