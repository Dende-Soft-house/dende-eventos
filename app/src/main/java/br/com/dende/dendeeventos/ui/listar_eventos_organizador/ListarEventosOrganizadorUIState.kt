package br.com.dende.dendeeventos.ui.listar_eventos_organizador

import br.com.dende.dendeeventos.domain.Evento

data class ListarEventosOrganizadorUIState(
    val isLoading: Boolean = false,
    val eventos: List<Evento> = emptyList(),
    val erroMensagem: String? = null
)