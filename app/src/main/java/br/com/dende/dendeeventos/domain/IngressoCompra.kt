import java.util.UUID

data class Ingresso(
    val id: String = UUID.randomUUID().toString(),
    val usuario: Usuário,
    val evento: Evento,
    var pagamento: Pagamento,
    val assento: String,
    var status: StatusIngresso = StatusIngresso.AGUARDANDO_PAGAMENTO
)
