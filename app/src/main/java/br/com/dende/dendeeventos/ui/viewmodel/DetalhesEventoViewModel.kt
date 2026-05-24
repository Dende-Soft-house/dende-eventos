package br.com.dende.dendeeventos.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.ui.data.EventData
import br.com.dende.dendeeventos.domain.model.EventCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetalhesEventoViewModel: ViewModel() {

    private val _evento = MutableStateFlow<EventCard?>(null)

    val evento: StateFlow<EventCard?>
        get() = _evento

    fun carregarEvento(id: Long?) {

        _evento.value = EventData.eventos.find {
            it.id == id
        }
    }
}