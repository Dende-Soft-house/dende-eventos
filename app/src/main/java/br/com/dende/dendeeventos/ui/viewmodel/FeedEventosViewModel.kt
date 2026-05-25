package br.com.dende.dendeeventos.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.ui.data.EventData
import br.com.dende.dendeeventos.domain.model.EventCardDataset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeedEventosViewModel: ViewModel() {

    private val _eventos = MutableStateFlow<List<EventCardDataset>>(emptyList())

    val eventos: StateFlow<List<EventCardDataset>>
        get() = _eventos

    //Inicia ao criar o viewmodel, e carrega automaticamente os eventos disponíveis
    init {
        carregarEventos()
    }

    //Obtém os eventos do dataset temporário
    private fun carregarEventos() {
        _eventos.value =
            EventData.eventos
    }
}