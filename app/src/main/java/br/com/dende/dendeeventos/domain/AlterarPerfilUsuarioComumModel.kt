package br.com.dende.dendeeventos.domain
import br.com.dende.dendeeventos.domain.PerfilUsuario

data class AlterarPerfilUsuarioComumModel (
    val perfilUsuario: PerfilUsuario,
    var perfilAtivo: Boolean = true
)