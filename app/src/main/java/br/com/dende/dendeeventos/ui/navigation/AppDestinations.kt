package br.com.dende.dendeeventos.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestinations {

    @Serializable
    data object Home: AppDestinations

    @Serializable
    data object Login: AppDestinations

    @Serializable
    data object EventDashboard : AppDestinations
}