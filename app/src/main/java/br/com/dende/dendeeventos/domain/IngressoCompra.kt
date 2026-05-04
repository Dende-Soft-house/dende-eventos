import java.util.UUID

data class Ingresso(
    val id: String = UUID.randomUUID().toString(),
    val usuarioId: String,
    val eventoId: String,
    val pagamentoId: String,
    val assento: String,
    var status: StatusIngresso = StatusIngresso.ATIVO
)
