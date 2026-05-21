package com.exemplo.projeto.repositories

import com.exemplo.projeto.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    // Não precisa escrever códigos SQL. O JpaRepository cria o .findById() e .save() sozinho.
}
