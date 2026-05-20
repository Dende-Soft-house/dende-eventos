package br.com.dende.dendeeventos.viewmodel

import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.data.EventData
import br.com.dende.dendeeventos.domain.model.EventCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeedEventosViewModel: ViewModel() {

    private val _eventos = MutableStateFlow<List<EventCard>>(emptyList())

    val eventos: StateFlow<List<EventCard>>
        get() = _eventos

    init {
        carregarEventos()
    }

    private fun carregarEventos() {
        _eventos.value =
            EventData.eventos
    }
}