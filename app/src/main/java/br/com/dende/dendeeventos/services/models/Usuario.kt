package com.exemplo.projeto.models

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
class Usuario(
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    val nome: String,
    
    // Campo booleano (true/false) que define se o usuário está ativo
    var ativo: Boolean = false 
)
