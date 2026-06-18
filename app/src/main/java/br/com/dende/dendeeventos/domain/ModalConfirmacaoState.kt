package br.com.dende.dendeeventos.domain

import br.com.dende.dendeeventos.ui.components.ModalConfirmacaoTipo

public data class ModalConfirmacaoState(
    val visible: Boolean = false,
    val tipo: ModalConfirmacaoTipo = ModalConfirmacaoTipo.INATIVAR,
    val confirmationText: String = "",
    val showError: Boolean = false
)
