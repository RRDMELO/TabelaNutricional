package com.rocketseat.RRM.tabelanutricional.domain.repository

import com.rocketseat.RRM.tabelanutricional.data.model.User

interface UserRepository {
    suspend fun registerUser(username: String, email: String, password: String): Boolean
    suspend fun loginUser(email: String, password: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun userExists(email: String): Boolean
}

