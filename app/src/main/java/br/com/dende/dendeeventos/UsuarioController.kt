package com.exemplo.projeto.controllers

import com.exemplo.projeto.services.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    // Define a rota PUT: /api/usuarios/{id}/reativar
    @PutMapping("/{id}/reativar")
    fun reativarUsuario(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        usuarioService.reativar(id)
        return ResponseEntity.ok(mapOf("mensagem" to "Usuário reativado com sucesso!"))
    }
}
