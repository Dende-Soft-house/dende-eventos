package br.com.dende.dendeeventos.ui.cadastrar_alterar_evento

import br.com.dende.dendeeventos.domain.Evento
import br.com.dende.dendeeventos.domain.ModalidadeEvento
import br.com.dende.dendeeventos.domain.TipoEvento

data class CadastrarAlterarEventoUIState(
    val nome: String = "",
    val paginaWeb: String = "",
    val descricao: String = "",
    val dataInicio: String = "",
    val dataFim: String = "",
    val erroNome: String? = null,
    val erroPaginaWeb: String? = null,
    val erroDescricao: String? = null,
    val erroDataInicio: Boolean = false,
    val erroDataFim: Boolean = false,
    val erroData: String? = null,
    val tipoEvento: TipoEvento = TipoEvento.OUTRO,
    val erroTipoEvento: String? = null,
    val eventoPrincipal: Evento? = null,
    val modalidadeEvento: ModalidadeEvento = ModalidadeEvento.HIBRIDO,
    val capacidadeMaxima: String = "",
    val local: String = "",
    val erroCapacidadeMaxima: String? = null,
    val erroLocal: String? = null,
    val precoTicket: String = "",
    val aceitaEstorno: Boolean = false,
    val taxaEstorno: String = "",
    val erroPrecoTicket: String? = null,
    val erroTaxaEstorno: String? = null,
    val urlBanner: String = ""
)