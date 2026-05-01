// Enum Class para tela de ativação e desativação de evento
enum class StatusOperacao {
    AGUARDANDO, SUCESSO, ERRO
}

data class EventoStatus(
    val eventoId: Int,
    var nomeEvento: String,
    var dataInicio: LocalDateTime,
    var dataFim: LocalDateTime,
    var localEvento: String,
    var imagemUrl: String,
    // Variável responsável por controlar o estado do evento (ativo/inativo)
    var isEventActive: Boolean = false,
    val statusOperacao: StatusOperacao = StatusOperacao.AGUARDANDO
)