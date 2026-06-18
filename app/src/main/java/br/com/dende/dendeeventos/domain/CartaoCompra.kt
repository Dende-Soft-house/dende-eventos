import java.time.LocalDate
import java.util.UUID

data class Cartao(
    val usuario: Usuario,
    val nomeTitular: String,
    val numero: String,
    val validade: LocalDate,
    val cvv: Int,
    val salvarParaOutrosPagamentos: Boolean = false
)
