// Enum Class para tela de ativação e desativação de evento
enum class EtapaDesativacaoEvento {
    AGUARDANDO_CONFIRMACAO, SUCESSO, ERRO
}

data class EventoStatus(
    /*val eventoId: Int,
    var nomeEvento: String,*/
    // Variável responsável por controlar o estado do evento (ativo/inativo)
    var ativo: Boolean = false,
    var statusOperacao: EtapaDesativacaoEvento = EtapaDesativacaoEvento.AGUARDANDO_CONFIRMACAO,
    var estadoCarregando: Boolean = false,
    var estadoSucesso: Boolean = false,
    var erro: String? = null,
    var confirmar: String? = "",
    var botaoLiberado: Boolean = false
)

/*data class EventoModalInteracoes(
    val estadoSucesso: Boolean = false,
    val erro: String? = null,
    val confirmar: String? = "",
    val botaoLiberado: Boolean = false
)*/
