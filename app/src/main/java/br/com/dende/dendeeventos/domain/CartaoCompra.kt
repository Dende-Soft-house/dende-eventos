import java.util.UUID

data class Cartao(
    val id: String = UUID.randomUUID().toString(),
    val usuario: Usuario,
    val nomeTitular: String,
    val numero: String,
    val validade: String,
    val cvv: String,
    val salvarParaFuturos: Boolean = false
)
