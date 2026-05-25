package br.com.dende.dendeeventos.feature.navigate

import kotlinx.serialization.Serializable

sealed interface AppDestinations {

    @Serializable
    data class AtivarEventoRoute(val eventoId: String) : AppDestinations

    @Serializable
    data object AtivarEventoOkRoute : AppDestinations

    @Serializable
    data class DesativarEventoRoute(val eventoId: String) : AppDestinations

    @Serializable
    data object DesativarEventoOkRoute : AppDestinations
}