package br.com.dende.dendeeventos.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListarIngressosViewModel : ViewModel() {

    // 0 = Ativos, 1 = Encerrados
    private val _abaSelecionada = MutableStateFlow(0)
    val abaSelecionada: StateFlow<Int> = _abaSelecionada.asStateFlow()

    fun selecionarAba(index: Int) {
        _abaSelecionada.value = index
    }
}
