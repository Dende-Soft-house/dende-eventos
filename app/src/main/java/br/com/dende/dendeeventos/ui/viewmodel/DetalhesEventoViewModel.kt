package br.com.dende.dendeeventos.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.ui.data.EventData
import br.com.dende.dendeeventos.domain.model.EventCardDataset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetalhesEventoViewModel: ViewModel() {

    //Estado do evento selecionado
    private val _evento = MutableStateFlow<EventCardDataset?>(null)
    val evento: StateFlow<EventCardDataset?>
        get() = _evento

    // Carrega um evento com base no ID recebido
    fun carregarEvento(id: Long) {

        _evento.value = EventData.eventos.find {
            it.id == id
        }
    }
}