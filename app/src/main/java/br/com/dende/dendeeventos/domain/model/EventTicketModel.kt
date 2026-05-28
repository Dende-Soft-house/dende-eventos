package br.com.dende.dendeeventos.domain.model

import java.time.Duration

// Enum que representa as categorias possíveis de um evento.

enum class StatusEvento {
    ATIVO, ENCERRADO, CANCELADO, EM_EXECUCAO
}



data class EventCard(
    val evento: Evento,
    val totalInscritos: Int,
    val ingressos: List<Ingresso>
) {

    //para pegar as fotos do usuario pra colocar no card do feed
    val participantesPreview: List<UsuarioComum>
        get() = ingressos.take(3).map {it.usuario}


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