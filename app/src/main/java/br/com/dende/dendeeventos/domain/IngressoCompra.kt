import java.util.UUID

data class Ingresso(
    val id: String = UUID.randomUUID().toString(),
    val usuario: Usuário,
    val evento: Evento,
    val pagamentoId: String,
    val assento: String,
    var status: StatusIngresso = StatusIngresso.ATIVO
)
