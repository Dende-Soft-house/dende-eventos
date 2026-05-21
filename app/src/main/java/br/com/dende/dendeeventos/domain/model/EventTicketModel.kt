package br.com.dende.dendeeventos.domain.model

import java.time.Duration

// Enum que representa as categorias possíveis de um evento.

enum class StatusEvento {
    ATIVO, ENCERRADO, CANCELADO, EM_EXECUCAO
}

// Enum que representa os status possíveis de um evento.


data class EventCard(
    var id: Long,
    val evento: Evento,
    val gratuito: Boolean,
    val totalInscritos: Int,
    val capacidade: Int,
    val local: Local,
    val categoriaEvento: TipoEvento,
    val status: StatusEvento,
    val urlImageBanner: String
) {
    fun calcularDuracao(): Duration {
        return Duration.between(
            evento.dataInicio,
            evento.dataFim
        )
    }
}

data class Ingresso(
    var id: Long,
    val evento: Evento,
    val categoriaEvento: CategoriaEvento,
    val usuario: Usuario,
    val statusEvento: StatusEvento,
    val urlQrCode: String,
    val urlImage: String
)