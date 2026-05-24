package br.com.dende.dendeeventos.ui.cancelamento

import androidx.compose.ui.graphics.Color

val OrangeLight = Color(0xFFFFF7F0)
val ScreenBackground = Color(0xFFFAFAFA)
val TextPrimary = Color(0xFF111111)
val TextSecondary = Color(0xFF757575)
val DividerColor = Color(0xFFE0E0E0)


data class TicketCancelUiState(
    val eventName: String = "Integra SI - Realizado pela UNEX",
    val eventDate: String = "25 Out 2022",
    val eventTime: String = "22:00",
    val seat: String = "05",
    val daysRemaining: String = "12 dias",
    val originalAmount: String = "R$ 1.250,00",
    val cancelFee: String = "−R$ 375,00",
    val refundAmount: String = "R$ 875,00",
    val refundPeriod: String = "3–7 dias antes",
    val selectedReason: CancelReason = CancelReason.CANNOT_ATTEND,
    val observation: String = ""
)

enum class CancelReason(val label: String) {
    CANNOT_ATTEND("Não posso comparecer ao evento"),
    PLAN_CHANGED("Mudança de planos"),
    HEALTH_OR_PERSONAL("Motivos de saúde / pessoais"),
    BETTER_TICKETS("Encontrei ingressos melhores"),
    OTHER("Outro motivo")
}
