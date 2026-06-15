package com.rocketseat.RRM.tabelanutricional.data.repository

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.UserDao
import com.rocketseat.RRM.tabelanutricional.data.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.MessageDigest

class UserRepositoryImplTest {

    private class FakeUserDao : UserDao {
        val users = mutableListOf<User>()
        var insertedUser: User? = null
        var getUserByEmailCalls = 0
        var lastEmailQuery: String? = null
        var clearAllUsersCalls = 0

        override suspend fun insertUser(user: User) {
            insertedUser = user
            users.add(user)
        }

        override suspend fun updateUser(user: User) = Unit

        override suspend fun getUserByEmail(email: String): User? {
            getUserByEmailCalls++
            lastEmailQuery = email
            return users.firstOrNull { it.email == email }
        }

        override suspend fun getUserByUsername(username: String): User? = users.firstOrNull { it.username == username }

        override suspend fun getUserById(id: Long): User? = users.firstOrNull { it.id == id }

        override suspend fun getAllUsers(): List<User> = users

        override suspend fun deleteUser(user: User) {
            users.remove(user)
        }

        override suspend fun clearAllUsers() {
            clearAllUsersCalls++
            users.clear()
        }

        override suspend fun updateUserProfileImage(email: String, imageUri: String) = Unit
    }

    private fun sha256Hex(value: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    @Test
    fun givenValidUser_whenRegister_thenHashesPasswordAndStoresUser() = runBlocking {
        val dao = FakeUserDao()
        val repository = UserRepositoryImpl(dao)

        val result = repository.registerUser(
            username = "Bernardo",
            email = "user@email.com",
            password = "123456"
        )

        assertTrue(result)
        assertEquals(1, dao.users.size)
        assertEquals("Bernardo", dao.insertedUser?.username)
        assertEquals("user@email.com", dao.insertedUser?.email)
        assertEquals(sha256Hex("123456"), dao.insertedUser?.password)
        assertNotEquals("123456", dao.insertedUser?.password)
    }

    @Test
    fun givenCorrectPassword_whenLogin_thenReturnsUser() = runBlocking {
        val dao = FakeUserDao()
        val repository = UserRepositoryImpl(dao)
        val storedUser = User(
            id = 1,
            email = "user@email.com",
            username = "Bernardo",
            password = sha256Hex("123456")
        )
        dao.users.add(storedUser)

        val result = repository.loginUser("user@email.com", "123456")

        assertEquals(storedUser, result)
        assertEquals(1, dao.getUserByEmailCalls)
        assertEquals("user@email.com", dao.lastEmailQuery)
    }

    @Test
    fun givenWrongPassword_whenLogin_thenReturnsNull() = runBlocking {
        val dao = FakeUserDao()
        val repository = UserRepositoryImpl(dao)
        dao.users.add(
            User(
                id = 1,
                email = "user@email.com",
                username = "Bernardo",
                password = sha256Hex("123456")
            )
        )

        val result = repository.loginUser("user@email.com", "wrong-password")

        assertNull(result)
    }

    @Test
    fun givenEmail_whenUserExists_thenReturnsTrueOrFalse() = runBlocking {
        val dao = FakeUserDao()
        val repository = UserRepositoryImpl(dao)
        dao.users.add(
            User(
                id = 1,
                email = "user@email.com",
                username = "Bernardo",
                password = sha256Hex("123456")
            )
        )

        assertTrue(repository.userExists("user@email.com"))
        assertFalse(repository.userExists("missing@email.com"))
    }
}

