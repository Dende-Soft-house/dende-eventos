package br.com.dende.dendeeventos.domain.model

import java.time.LocalDateTime

enum class CategoriaEvento {
    MUSICA, GASTRONOMIA, ESPORTES, DESIGN, TECNOLOGIA, EDUCACAO, NEGOCIOS, SAUDE, ARTE, CULTURA, FESTA, WORKSHOP, PALESTRA
}

// Enum que representa as categorias possíveis de um evento.

data class Evento(
    val eventoId: String,
    val nomeEvento: String,
    val descricao: String,
    val dataHora: LocalDateTime,
    val duracaoMinutos: Int,
    val gratuito: Boolean,
    val totalParticipantes: Int,
    val salvo: Boolean,
    val local: Local,
    val  categoriaEvento: CategoriaEvento
)

// Representa o local o evento ocorre
data class Local(
    val id: String,
    val nome: String,
    val cidade: String
)

data class Ingresso(
    val id: String,
    val evento: Evento,
    val tipo: String,
    val nomeUsuario: String,
    val statusIngresso: Boolean,
    val qrCode: String
)