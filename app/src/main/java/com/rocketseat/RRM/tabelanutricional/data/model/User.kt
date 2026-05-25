package com.rocketseat.RRM.tabelanutricional.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val username: String,
    val password: String, // Deve ser armazenado de forma segura (hash)
    val profileImageUri: String? = null, // URI da imagem de perfil do usuário
    val createdAt: Long = System.currentTimeMillis()
)

