package br.com.dende.dendeeventos.ui.navigation

object AppDestinations {

    const val FEED = "feed"

    const val DETALHES = "detalhes/{eventoId}"

    fun detalhesRoute(
        eventoId: Long
    ) = "detalhes/$eventoId"
}