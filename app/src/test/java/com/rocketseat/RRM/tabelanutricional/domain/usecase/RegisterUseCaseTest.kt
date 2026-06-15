package com.rocketseat.RRM.tabelanutricional.domain.usecase

import com.rocketseat.RRM.tabelanutricional.data.model.User
import com.rocketseat.RRM.tabelanutricional.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterUseCaseTest {

    private class FakeUserRepository : UserRepository {
        var userExistsResult = false
        var registerResult = true
        var userExistsCalls = 0
        var registerCalls = 0
        var lastRegisteredUsername: String? = null
        var lastRegisteredEmail: String? = null
        var lastRegisteredPassword: String? = null

        override suspend fun registerUser(username: String, email: String, password: String): Boolean {
            registerCalls++
            lastRegisteredUsername = username
            lastRegisteredEmail = email
            lastRegisteredPassword = password
            return registerResult
        }

        override suspend fun loginUser(email: String, password: String): User? = null

        override suspend fun getUserByEmail(email: String): User? = null

        override suspend fun userExists(email: String): Boolean {
            userExistsCalls++
            return userExistsResult
        }
    }

    @Test
    fun givenEmptyFields_whenRegister_thenReturnsFalseAndDoesNotCallRepository() = runBlocking {
        val repository = FakeUserRepository()
        val useCase = RegisterUseCase(repository)

        val result = useCase(username = "", email = "user@email.com", password = "123456")

        assertFalse(result)
        assertEquals(0, repository.userExistsCalls)
        assertEquals(0, repository.registerCalls)
    }

    @Test
    fun givenInvalidEmailSyntax_whenRegister_thenReturnsFalseAndDoesNotCallRepository() = runBlocking {
        val repository = FakeUserRepository()
        val useCase = RegisterUseCase(repository)

        val result = useCase(username = "Bernardo", email = "user-email.com", password = "123456")

        assertFalse(result)
        assertEquals(0, repository.userExistsCalls)
        assertEquals(0, repository.registerCalls)
    }

    @Test
    fun givenExistingUser_whenRegister_thenReturnsFalseAndDoesNotRegisterAgain() = runBlocking {
        val repository = FakeUserRepository().apply {
            userExistsResult = true
        }
        val useCase = RegisterUseCase(repository)

        val result = useCase(username = "Bernardo", email = "user@email.com", password = "123456")

        assertFalse(result)
        assertEquals(1, repository.userExistsCalls)
        assertEquals(0, repository.registerCalls)
    }

    @Test
    fun givenValidData_whenRegister_thenDelegatesToRepository() = runBlocking {
        val repository = FakeUserRepository().apply {
            userExistsResult = false
            registerResult = true
        }
        val useCase = RegisterUseCase(repository)

        val result = useCase(username = "Bernardo", email = "user@email.com", password = "123456")

        assertTrue(result)
        assertEquals(1, repository.userExistsCalls)
        assertEquals(1, repository.registerCalls)
        assertEquals("Bernardo", repository.lastRegisteredUsername)
        assertEquals("user@email.com", repository.lastRegisteredEmail)
        assertEquals("123456", repository.lastRegisteredPassword)
    }
}

