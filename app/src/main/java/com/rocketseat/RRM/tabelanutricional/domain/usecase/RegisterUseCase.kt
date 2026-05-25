package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.domain.repository.UserRepository

class RegisterUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String): Boolean {
        // Validação básica
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            return false
        }

        if (!isValidEmail(email)) {
            return false
        }

        // Verificar se usuário já existe
        if (userRepository.userExists(email)) {
            return false
        }

        return userRepository.registerUser(username, email, password)
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}

