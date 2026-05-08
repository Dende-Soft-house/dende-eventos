package br.com.dende.dendeeventos.domain.model

import java.time.LocalDateTime
import java.time.Period

enum class CategoriaEvento {
    MUSICA, GASTRONOMIA, ESPORTES, DESIGN, TECNOLOGIA, EDUCACAO, NEGOCIOS, SAUDE, ARTE, CULTURA, FESTA, WORKSHOP, PALESTRA
}

// Enum que representa as categorias possíveis de um evento.

enum class StatusEvento {
    ATIVO, ENCERRADO, CANCELADO
}

// Enum que representa os status possíveis de um evento.


data class EventCard(
    var id: Long,
    val evento: String,
    val descricao: String,
    val dataHora: LocalDateTime,
    val dataInicio: LocalDateTime,
    val dataFim: LocalDateTime,
    val duracao: Period,
    val gratuito: Boolean,
    val totalInscritos: Int,
    val capacidade: Int,
    val local: Local,
    val categoriaEvento: CategoriaEvento,
    val status: StatusEvento,
    val urlImageBanner: String
) {
    fun calcularDataFim(): LocalDateTime {

        return dataInicio.plus(duracao)
    }
}

// Representa o local o evento ocorre
data class Local(
    val nome: String,
    val cidade: String
)

data class Ingresso(
    var id: Long,
    val evento: EventCard,
    val categoriaEvento: CategoriaEvento,
    val nomeUsuario: Usuario,
    val statusEvento: StatusEvento,
    val urlQrCode: String,
    val urlImage: String
)