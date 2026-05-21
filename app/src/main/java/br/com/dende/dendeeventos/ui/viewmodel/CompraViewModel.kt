package br.com.dende.dendeeventos.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.dende.dendeeventos.domain.Cartao
import br.com.dende.dendeeventos.domain.EventoStatus
import br.com.dende.dendeeventos.domain.Genero
import br.com.dende.dendeeventos.domain.Ingresso
import br.com.dende.dendeeventos.domain.Pagamento
import br.com.dende.dendeeventos.domain.StatusIngresso
import br.com.dende.dendeeventos.domain.StatusPagamento
import br.com.dende.dendeeventos.domain.TipoPagamento
import br.com.dende.dendeeventos.domain.Usuario
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

private const val MAX_CARTOES = 4

data class CompraUiState(
    val quantidade: Int = 2,
    val precoUnitario: Double = 1562.50,
    val valorTotal: Double = quantidade * precoUnitario,
    val numeroCartao: String = "",
    val nomeTitular: String = "",
    val validade: String = "",
    val cvv: String = "",
    val salvarCartao: Boolean = true,
    val tentouSalvarCartao: Boolean = false,
    val tentouConfirmarPagamento: Boolean = false,
    val cartoesSalvos: List<Cartao> = emptyList(),
    val cartaoSelecionadoId: String? = null,
    val statusPagamento: StatusPagamento = StatusPagamento.PENDENTE,
    val cartao: Cartao? = null,
    val ingresso: Ingresso? = null,
    val pagamento: Pagamento? = null
) {
    val cartaoDuplicado: Boolean
        get() = numeroCartao.isNotEmpty() && cartoesSalvos.any { it.numero == numeroCartao }

    val numeroCartaoValido: Boolean
        get() = numeroCartao.length == 16 && numeroCartao.isValidCardNumber() && !cartaoDuplicado

    val nomeTitularValido: Boolean
        get() = nomeTitular.isNotBlank()

    val validadeValida: Boolean
        get() {
            if (validade.length != 4) return false
            val mes = validade.take(2).toIntOrNull() ?: return false
            if (mes !in 1..12) return false

            val ano = 2000 + (validade.drop(2).toIntOrNull() ?: return false)
            return !YearMonth.of(ano, mes).isBefore(YearMonth.now())
        }

    val cvvValido: Boolean
        get() = cvv.length == 3

    val cartaoValido: Boolean
        get() = numeroCartaoValido && nomeTitularValido && validadeValida && cvvValido

    val limiteCartoesAtingido: Boolean
        get() = cartoesSalvos.size >= MAX_CARTOES

    val cartaoSelecionado: Cartao?
        get() = cartoesSalvos.firstOrNull { it.id == cartaoSelecionadoId }
}

class CompraViewModel {
    var uiState by mutableStateOf(CompraUiState())
        private set

    fun aumentarQuantidade() {
        atualizarQuantidade(uiState.quantidade + 1)
    }

    fun diminuirQuantidade() {
        if (uiState.quantidade > 1) {
            atualizarQuantidade(uiState.quantidade - 1)
        }
    }

    fun atualizarNumeroCartao(numero: String) {
        uiState = uiState.copy(numeroCartao = numero.filter { it.isDigit() }.take(16))
    }

    fun atualizarNomeTitular(nome: String) {
        uiState = uiState.copy(nomeTitular = nome)
    }

    fun atualizarValidade(validade: String) {
        uiState = uiState.copy(validade = validade.filter { it.isDigit() }.take(4))
    }

    fun atualizarCvv(cvv: String) {
        uiState = uiState.copy(cvv = cvv.filter { it.isDigit() }.take(3))
    }

    fun selecionarCartao(cartaoId: String) {
        uiState = uiState.copy(
            cartaoSelecionadoId = cartaoId,
            tentouConfirmarPagamento = false,
            statusPagamento = StatusPagamento.PENDENTE
        )
    }

    fun alternarSalvarCartao() {
        uiState = uiState.copy(salvarCartao = !uiState.salvarCartao)
    }

    fun salvarCartao(): Boolean {
        if (uiState.limiteCartoesAtingido) {
            uiState = uiState.copy(tentouSalvarCartao = true)
            return false
        }

        if (!uiState.cartaoValido || uiState.cartaoDuplicado) {
            uiState = uiState.copy(tentouSalvarCartao = true)
            return false
        }

        val cartao = Cartao(
            usuarioId = "usuario@teste.com",
            nomeTitular = uiState.nomeTitular.trim(),
            numero = uiState.numeroCartao,
            validade = parseValidade(uiState.validade),
            cvv = uiState.cvv.toInt(),
            salvarParaOutrosPagamentos = uiState.salvarCartao
        )

        uiState = uiState.copy(
            numeroCartao = "",
            nomeTitular = "",
            validade = "",
            cvv = "",
            salvarCartao = true,
            tentouSalvarCartao = false,
            cartoesSalvos = uiState.cartoesSalvos + cartao,
            cartaoSelecionadoId = cartao.id,
            tentouConfirmarPagamento = false,
            statusPagamento = StatusPagamento.PENDENTE
        )

        return true
    }

    fun limparFormularioCartao() {
        uiState = uiState.copy(
            numeroCartao = "",
            nomeTitular = "",
            validade = "",
            cvv = "",
            salvarCartao = true,
            tentouSalvarCartao = false
        )
    }

    fun limparCartoesTemporariosNaoSelecionados() {
        val selecionadoId = uiState.cartaoSelecionadoId
        val cartoesMantidos = uiState.cartoesSalvos.filter { cartao ->
            cartao.salvarParaOutrosPagamentos || cartao.id == selecionadoId
        }
        val selecaoMantida = cartoesMantidos.any { it.id == selecionadoId }

        uiState = uiState.copy(
            cartoesSalvos = cartoesMantidos,
            cartaoSelecionadoId = if (selecaoMantida) selecionadoId else null
        )
    }

    fun confirmarPagamento() {
        val cartaoSelecionado = uiState.cartaoSelecionado

        if (cartaoSelecionado == null) {
            uiState = uiState.copy(
                tentouConfirmarPagamento = true,
                statusPagamento = StatusPagamento.PENDENTE,
                cartao = null,
                ingresso = null,
                pagamento = null
            )
            return
        }

        val usuario = Usuario(
            nome = "Usuario Teste",
            email = "usuario@teste.com",
            senha = "123456",
            genero = Genero.OUTROS,
            dataNascimento = LocalDate.of(2000, 1, 1)
        )
        val evento = EventoStatus(
            eventoId = 1,
            nomeEvento = "Integra SI - Realizado Pela Unex",
            ativo = true
        )
        val ingresso = Ingresso(
            usuario = usuario,
            evento = evento,
            assento = "Livre",
            status = StatusIngresso.ATIVO
        )
        val pagamento = Pagamento(
            ingressoId = ingresso.id,
            eventoId = evento.eventoId,
            cartaoId = cartaoSelecionado.id,
            tipoPagamento = TipoPagamento.CARTAO,
            quantidade = uiState.quantidade,
            precoUnitario = uiState.precoUnitario,
            valorTotal = uiState.valorTotal,
            status = StatusPagamento.CONFIRMADO,
            dataHora = LocalDateTime.now()
        )

        uiState = uiState.copy(
            tentouConfirmarPagamento = false,
            statusPagamento = StatusPagamento.CONFIRMADO,
            cartao = cartaoSelecionado,
            ingresso = ingresso,
            pagamento = pagamento
        )
    }

    private fun atualizarQuantidade(novaQuantidade: Int) {
        uiState = uiState.copy(
            quantidade = novaQuantidade,
            valorTotal = novaQuantidade * uiState.precoUnitario,
            statusPagamento = StatusPagamento.PENDENTE
        )
    }

    private fun parseValidade(validade: String): LocalDate {
        val mes = validade.take(2).toInt()
        val ano = 2000 + validade.drop(2).toInt()
        return LocalDate.of(ano, mes, 1)
    }
}

private fun String.isValidCardNumber(): Boolean {
    var sum = 0
    var doubleDigit = false

    for (index in length - 1 downTo 0) {
        var digit = this[index].digitToInt()

        if (doubleDigit) {
            digit *= 2
            if (digit > 9) digit -= 9
        }

        sum += digit
        doubleDigit = !doubleDigit
    }

    return sum % 10 == 0
}
