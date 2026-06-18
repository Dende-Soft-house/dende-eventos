package br.com.dende.dendeeventos.ui.user

import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.core.designsystem.components.ModalConfirmacaoTipo
import br.com.dende.dendeeventos.domain.InativarUsuario
import br.com.dende.dendeeventos.domain.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class InactivateUserUiState(
    val inativarUsuario: InativarUsuario? = null,
    val isConfirmModalVisible: Boolean = false,
    val isSuccessModalVisible: Boolean = false,
    val confirmationText: String = "",
    val modalTipo: ModalConfirmacaoTipo = ModalConfirmacaoTipo.INATIVAR
)

class InactivateUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InactivateUserUiState())
    val uiState: StateFlow<InactivateUserUiState> = _uiState.asStateFlow()

    fun initialize(usuario: Usuario) {
        _uiState.update { 
            it.copy(inativarUsuario = InativarUsuario(usuario = usuario, ativo = usuario.ativo)) 
        }
    }

    fun onToggleStatusRequest() {
        val currentAtivo = _uiState.value.inativarUsuario?.ativo ?: true
        val tipo = if (currentAtivo) ModalConfirmacaoTipo.INATIVAR else ModalConfirmacaoTipo.REATIVAR
        _uiState.update { it.copy(
            isConfirmModalVisible = true,
            modalTipo = tipo,
            confirmationText = ""
        ) }
    }

    fun onConfirmationTextChange(text: String) {
        _uiState.update { it.copy(confirmationText = text) }
    }

    fun onConfirmStatusChange() {
        _uiState.update { state ->
            val currentInativar = state.inativarUsuario
            if (currentInativar != null) {
                val updatedInativar = currentInativar.copy(ativo = !currentInativar.ativo)
                state.copy(
                    inativarUsuario = updatedInativar,
                    isConfirmModalVisible = false,
                    isSuccessModalVisible = true
                )
            } else {
                state
            }
        }
    }

    fun onDismissConfirm() {
        _uiState.update { it.copy(isConfirmModalVisible = false) }
    }

    fun onDismissSuccess() {
        _uiState.update { it.copy(isSuccessModalVisible = false) }
    }
    
    fun onEditProfile() {

    }
}
