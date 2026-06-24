package br.com.dende.dendeeventos.ui.cancelamento

data class CancelamentoUiState(
    val motivoSelecionado: String = "",
    val observacao: String = "",
    val exibirConfirmacao: Boolean = false,
    val carregando: Boolean = false,
    val erro: String? = null,
    val cancelamentoConcluido: Boolean = false
)
