package br.com.dende.dendeeventos.domain


import EventoStatus
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.savedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException


class EventStatusViewModel : ViewModel (){
    private val _uiState = MutableStateFlow(EventoStatus())
    val uiState: StateFlow<EventoStatus> = _uiState.asStateFlow()

    fun confirmar(text: String){
        _uiState.update {
            it.copy(
                confirmar = text,
                botaoLiberado = text == "CONFIRMAR"
            )
        }
    }

    fun ativar(eventoId: String) {
        if (!_uiState.value.botaoLiberado) return
        viewModelScope.launch {
            _uiState.update { it.copy(estadoCarregando = true, erro = null) }
            try {
                _uiState.update { it.copy(estadoCarregando = false, estadoSucesso = true) }
            }catch (e: Exception){
                _uiState.update { it.copy(estadoCarregando = false, erro = e.message) }
            }
        }
    }

    fun desativar(eventoId: String){
        if (!_uiState.value.botaoLiberado) return
        viewModelScope.launch {
            _uiState.update { it.copy(estadoCarregando = true, erro = null) }
            try {
                _uiState.update { it.copy(estadoCarregando = false, estadoSucesso = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(estadoCarregando = false, erro = e.message) }
            }
        }
    }

    fun limpandoEstado(){
        _uiState.update {
            EventoStatus()
        }
    }
}