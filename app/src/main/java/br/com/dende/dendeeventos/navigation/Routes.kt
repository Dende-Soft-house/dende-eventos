package br.com.dende.dendeeventos.navigation

/**
 * Rotas de navegação do fluxo de Checkout e Cancelamento.
 *
 * Fluxo:
 *  Checkout -> Pagamento -> (CadastroCartao) -> Ingresso ->
 *  PolicyCancel -> CancelReason -> RefundResume ->
 *  ConfirmModal -> ConfirmCancel -> Ingresso
 */
object Routes {
    const val CHECKOUT = "checkout"
    const val PAGAMENTO = "pagamento"
    const val CADASTRO_CARTAO = "cadastro_cartao"
    const val INGRESSO = "ingresso"
    const val POLICY_CANCEL = "policy_cancel"
    const val CANCEL_REASON = "cancel_reason"
    const val REFUND_RESUME = "refund_resume"
    const val CONFIRM_MODAL = "confirm_modal"
    const val CONFIRM_CANCEL = "confirm_cancel"
}
