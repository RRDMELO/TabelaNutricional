package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.data.model.User
import com.rocketseat.RRM.tabelanutricional.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        return userRepository.loginUser(email, password)
    }
}

