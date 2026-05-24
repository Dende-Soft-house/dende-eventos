package br.com.dende.dendeeventos.ui.fluxo

/**
 * Rotas do fluxo de Compra + Cancelamento.
 *
 * Os nomes foram mantidos exatamente como aparecem no diagrama de fluxo
 * (inclusive os erros de grafia "Casdastro_Cartao" e "Comfirm_Modal") para
 * facilitar a comparação visual entre código e diagrama.
 */
object FluxoRoutes {

    // ----- Fluxo de Compra (telas pertencentes a outro grupo) -----
    const val CHECKOUT = "Checkout"
    const val PAGAMENTO = "pagamento"
    const val CASDASTRO_CARTAO = "Casdastro_Cartao"
    const val INGRESSO = "Ingresso"

    // ----- Fluxo de Cancelamento -----
    const val POLICY_CANCEL = "Policy_Cancel"
    const val CANCEL_REASON = "Cancel_Reason"
    const val REFUND_RESUME = "Refund_Resume"
    const val COMFIRM_MODAL = "Comfirm_Modal"
    const val CONFIRM_CANCEL = "Confirm_Cancel"
}
