package br.com.dende.dendeeventos.ui.cadastrar_alterar_evento

import androidx.lifecycle.ViewModel
import br.com.dende.dendeeventos.domain.Evento
import br.com.dende.dendeeventos.domain.Faturamento
import br.com.dende.dendeeventos.domain.StatusEvento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CadastrarAlterarEventoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CadastrarAlterarEventoUIState())
    val uiState: StateFlow<CadastrarAlterarEventoUIState> = _uiState.asStateFlow()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun carregarEventoParaAlterar(evento: Evento?) {
        if (evento == null) {
            limparEstado()
            return
        }

        updateState {
            it.copy(
                nome = evento.nome, paginaWeb = evento.paginaWeb, descricao = evento.descricao,
                dataInicio = evento.dataInicio.format(dateFormatter), dataFim = evento.dataFim.format(dateFormatter),
                tipoEvento = evento.tipoEvento, modalidadeEvento = evento.modalidadeEvento,
                capacidadeMaxima = evento.capacidadeMaxima.toString(), local = evento.local,
                precoTicket = evento.faturamento.precoTicket.toString(), aceitaEstorno = evento.faturamento.aceitaEstorno,
                taxaEstorno = evento.faturamento.taxaEstorno.toString(), urlBanner = evento.urlBanner ?: ""
            )
        }
    }

    fun updateState(update: (CadastrarAlterarEventoUIState) -> CadastrarAlterarEventoUIState) {
        _uiState.update(update)
    }

    fun validarInformacoesBasicas(): Boolean {
        val state = _uiState.value

        val erroNome = when {
            state.nome.isBlank() -> "Campo obrigatório"
            state.nome.length < 3 -> "Nome muito curto"
            else -> null
        }

        val erroDescricao = when {
            state.descricao.isBlank() -> "Campo obrigatório"
            state.descricao.length < 5 -> "Descrição muito curta"
            else -> null
        }

        val erroPaginaWeb = when {
            state.paginaWeb.isNotEmpty() && !state.paginaWeb.contains(".") -> "Página web inválida"
            else -> null
        }

        val (dataErroInicio, dataErroFim, mensagemErroData) = try {
            val inicio = LocalDateTime.parse(state.dataInicio, dateFormatter)
            val fim = LocalDateTime.parse(state.dataFim, dateFormatter)
            val agora = LocalDateTime.now()

            val erro = when {
                inicio.isBefore(agora) || fim.isBefore(agora) -> "Datas devem ser futuras."
                fim.isBefore(inicio) || fim.isEqual(inicio) -> "Data fim deve ser após início."
                Duration.between(inicio, fim).toMinutes() < 30 -> "Duração mínima de 30 minutos."
                else -> null
            }
            Triple(erro != null && (inicio.isBefore(agora)), erro != null && !inicio.isBefore(agora), erro)
        } catch (e: Exception) {
            Triple(true, true, "Formato de data inválido.")
        }

        updateState {
            it.copy(
                erroNome = erroNome, erroDescricao = erroDescricao, erroPaginaWeb = erroPaginaWeb,
                erroDataInicio = dataErroInicio, erroDataFim = dataErroFim, erroData = mensagemErroData
            )
        }

        return erroNome == null && erroDescricao == null && erroPaginaWeb == null && mensagemErroData == null
    }

    fun validarInformacoesAdicionais(): Boolean {
        val state = _uiState.value

        val erroCapacidade = when {
            state.capacidadeMaxima.isBlank() -> "Campo obrigatório"
            state.capacidadeMaxima.toIntOrNull() == null || state.capacidadeMaxima.toInt() <= 0 -> "Capacidade inválida"
            else -> null
        }

        val erroLocal = if (state.local.isBlank()) "Campo obrigatório" else null

        updateState { it.copy(erroCapacidadeMaxima = erroCapacidade, erroLocal = erroLocal) }

        return erroCapacidade == null && erroLocal == null
    }

    fun validarFaturamento(): Boolean {
        val state = _uiState.value
        val preco = state.precoTicket.replace(",", ".").toDoubleOrNull()
        val taxa = state.taxaEstorno.replace(",", ".").toDoubleOrNull()

        val erroPreco = when {
            state.precoTicket.isBlank() -> "Campo obrigatório"
            preco == null || preco < 0.0 -> "Valor inválido"
            else -> null
        }

        val erroTaxa = when {
            !state.aceitaEstorno -> null
            state.taxaEstorno.isBlank() -> "Campo obrigatório"
            taxa == null || taxa !in 0.0..100.0 -> "Taxa deve ser entre 0 e 100%"
            else -> null
        }

        updateState { it.copy(erroPrecoTicket = erroPreco, erroTaxaEstorno = erroTaxa) }

        return erroPreco == null && erroTaxa == null
    }

    fun validarBanner(): Boolean {
        val state = _uiState.value

        return true
    }

    fun eventoParaSalvar(): Evento? {
        val state = _uiState.value

        val preco = state.precoTicket.replace(",", ".").toBigDecimalOrNull() ?: BigDecimal.ZERO
        val taxa = state.taxaEstorno.replace(",", ".").toBigDecimalOrNull() ?: BigDecimal.ZERO

        return try {
            Evento(
                eventoId = 0L,
                status = StatusEvento.ATIVO,
                organizador = 0L,
                nome = state.nome,
                paginaWeb = state.paginaWeb,
                descricao = state.descricao,
                dataInicio = LocalDateTime.parse(state.dataInicio, dateFormatter),
                dataFim = LocalDateTime.parse(state.dataFim, dateFormatter),
                tipoEvento = state.tipoEvento,
                eventoPrincipal = state.eventoPrincipal,
                modalidadeEvento = state.modalidadeEvento,
                capacidadeMaxima = state.capacidadeMaxima.toInt(),
                local = state.local,
                faturamento = Faturamento(preco, state.aceitaEstorno, taxa)
            )
        } catch (e: Exception) {
            null
        }
    }

    fun limparEstado() {
        _uiState.value = CadastrarAlterarEventoUIState()
    }
}