package br.com.dende.dendeeventos.domain

enum class TipoMotivoCancelamento(val descricao: String) {
    NAO_POSSO_COMPARECER("Não posso comparecer ao evento"),
    MUDANCA_DE_PLANOS("Mudança de planos"),
    MOTIVOS_SAUDE_OU_PESSOAIS("Motivos de saúde / pessoais"),
    ENCONTREI_INGRESSOS_MELHORES("Encontrei ingressos melhores"),
    OUTRO("Outro motivo")
}
