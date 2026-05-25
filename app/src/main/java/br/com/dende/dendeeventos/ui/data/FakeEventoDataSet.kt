package br.com.dende.dendeeventos.ui.data

import java.time.Period
import br.com.dende.dendeeventos.domain.model.EventCardDataset
import br.com.dende.dendeeventos.domain.model.CategoriaEvento
import br.com.dende.dendeeventos.domain.model.Local
import br.com.dende.dendeeventos.domain.model.StatusEvento
import java.time.LocalDateTime


//SUBSTITUIR POR DATASET OFICIAL APÓS MERGE JUNTO AS DEPENDENCIAS DOS OUTROS GRUPOS
object EventData {
    val eventos = listOf(

        EventCardDataset(
            id = 1,
            evento = "IntegraSI FSA",
            sobreEvento = "O IntegraSI FSA é um evento para estudantes, profissionais e entusiastas da área de Tecnologia da Informação que buscam aprender, se atualizar e se conectar com outras pessoas do meio.",
            descricao = "Um encontro de tecnologia, inovação e conexão",
            dataHora = LocalDateTime.now(),
            dataInicio = LocalDateTime.of(
                2026, 4, 21, 18, 50
            ),
            dataFim = LocalDateTime.of(
                2026, 4, 21, 22, 20
            ),
            duracao = Period.ofDays(1),
            gratuito = true,
            totalInscritos = 120,
            capacidade = 300,
            local = Local(
                nome = "UNEX",
                cidade = "Feira de Santana"
            ),
            categoriaEvento =
                CategoriaEvento.TECNOLOGIA,

            status =
                StatusEvento.ATIVO,

            urlImageBanner = ""
        ),

        EventCardDataset(
            id = 2,
            evento = "DevOps Days",
            sobreEvento = "Um encontro de tecnologia, inovação e conexão",
            descricao = "Evento sobre cultura DevOps",
            dataHora = LocalDateTime.now(),
            dataInicio = LocalDateTime.of(
                2026, 3, 23, 14, 0
            ),
            dataFim = LocalDateTime.of(
                2026, 3, 23, 18, 0
            ),
            duracao = Period.ofDays(1),
            gratuito = false,
            totalInscritos = 80,
            capacidade = 200,
            local = Local(
                nome = "UNEX",
                cidade = "Feira de Santana"
            ),
            categoriaEvento =
                CategoriaEvento.TECNOLOGIA,

            status =
                StatusEvento.ATIVO,

            urlImageBanner = ""
        )
    )
}