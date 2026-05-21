import java.util.UUID
import br.com.dende.dendeeventos.components.StatusPagamento

data class Pagamento(
    val id: String = UUID.randomUUID().toString(),
    val ingresso: Ingresso,
    val tipoPagamento: TipoPagamento,
    val cartao: Cartao,
    val quantidade: Int,
    val valorTotal: BigDecimal,
    var status: StatusPagamento = StatusPagamento.PENDENTE,
    val dataHora: LocalDateTime = LocalDateTime.now()
)
