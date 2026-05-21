package com.dendesofthouse.service

import com.dendesofthouse.domain.Usuario
import com.dendesofthouse.domain.StatusUsuario
import com.dendesofthouse.repository.UsuarioRepository
import java.time.LocalDateTime

class UsuarioService(private val repository: UsuarioRepository) {

    fun reativar(id: Long): Usuario {
        val usuario = repository.buscarPorId(id) 
            ?: throw IllegalStateException("Usuário axé não encontrado!")

        if (usuario.status == StatusUsuario.ATIVO) {
            throw IllegalStateException("Este usuário já está na ativa!")
        }

        return usuario.apply {
            status = StatusUsuario.ATIVO
            reativadoEm = LocalDateTime.now()
        }.also {
            repository.atualizar(it)
        }
    }
}
