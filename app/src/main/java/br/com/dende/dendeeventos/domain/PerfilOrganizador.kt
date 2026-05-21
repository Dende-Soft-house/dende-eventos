package br.com.dende.dendeeventos.domain

data class PerfilOrganizador(
    override var nome: String,
    override var imagemURL: String,
    override var dataNascimento: LocalDate,
    override var genero: Genero,
    override var email: String,
    var DadosEmpresa: Empresa?,
) : PerfilUsuario(nome, imagemURL, dataNascimento, genero, email)
