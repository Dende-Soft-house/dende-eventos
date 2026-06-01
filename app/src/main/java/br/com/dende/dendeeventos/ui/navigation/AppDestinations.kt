package br.com.dende.dendeeventos.ui.navigation

object AppDestinations {

    //Rpta da tela inicial
    const val FEED = "feed"

    // Rota da tela de detalhes do evento
    const val DETALHES = "detalhes/{eventoId}"


    fun detalhesRoute(
        eventoId: Long
    ) = "detalhes/$eventoId"
}