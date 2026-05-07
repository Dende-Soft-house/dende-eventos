// Enum Class para tela de ativação e desativação de evento
enum class EtapaDesativacaoEvento {
    AGUARDANDO_CONFIRMACAO, DESATIVADO, ERRO
}

data class EventoStatus(
    val eventoId: Int,
    var nomeEvento: String,
    // Variável responsável por controlar o estado do evento (ativo/inativo)
    var ativo: Boolean = false,
    val statusOperacao: StatusOperacao = StatusOperacao.AGUARDANDO
)
