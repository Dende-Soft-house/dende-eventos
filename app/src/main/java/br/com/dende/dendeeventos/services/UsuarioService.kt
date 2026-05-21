package com.exemplo.projeto.services

import com.exemplo.projeto.models.Usuario
import com.exemplo.projeto.repositories.UsuarioRepository
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun reativar(id: Long) {
        // 1. Busca o usuário no banco. Se não achar, joga erro 404
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.") }

        // 2. Regra de negócio: impede reativar quem já está ativo (Erro 400)
        if (usuario.ativo) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Este usuário já está ativo.")
        }

        // 3. Modifica o status e salva a alteração no banco de dados
        usuario.ativo = true
        usuarioRepository.save(usuario)
    }
}
