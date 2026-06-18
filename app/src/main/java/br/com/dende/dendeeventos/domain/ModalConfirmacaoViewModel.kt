package br.com.dende.dendeeventos.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dende.dendeeventos.ui.components.ModalConfirmacaoTipo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ModalConfirmacaoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ModalConfirmacaoState())
    val uiState: StateFlow<ModalConfirmacaoState> = _uiState.asStateFlow()

    private var errorTimerJob: Job? = null

    fun abrirModal(tipo: ModalConfirmacaoTipo) {
        _uiState.update {
            it.copy(
                visible = true,
                tipo = tipo,
                confirmationText = "",
                showError = false
            )
        }

        errorTimerJob?.cancel()
    }

    fun fecharModal() {
        _uiState.update {
            it.copy(
                visible = false,
                confirmationText = "",
                showError = false
            )
        }

        errorTimerJob?.cancel()
    }

    fun onConfirmationTextChange(newText: String) {
        _uiState.update {
            it.copy(
                confirmationText = newText,
                showError = false
            )
        }

        errorTimerJob?.cancel()
    }

    fun confirmarAcao(): Boolean {
        return if (isTextoConfirmacaoValido()) {
            fecharModal()
            true
        } else {
            exibirErroTemporario()
            false
        }
    }

    private fun isTextoConfirmacaoValido(): Boolean {
        return _uiState.value.confirmationText
            .trim()
            .equals("CONFIRMAR", ignoreCase = true)
    }

    private fun exibirErroTemporario() {
        _uiState.update {
            it.copy(showError = true)
        }

        errorTimerJob?.cancel()

        errorTimerJob = viewModelScope.launch {
            delay(10_000L)

            _uiState.update {
                it.copy(showError = false)
            }
        }
    }
}