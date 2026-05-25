package com.rocketseat.RRM.tabelanutricional.data.repository

import com.rocketseat.RRM.tabelanutricional.data.datasource.local.UserDao
import com.rocketseat.RRM.tabelanutricional.data.model.User
import com.rocketseat.RRM.tabelanutricional.domain.repository.UserRepository
import java.security.MessageDigest

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun registerUser(username: String, email: String, password: String): Boolean {
        return try {
            val hashedPassword = hashPassword(password)
            val user = User(
                username = username,
                email = email,
                password = hashedPassword
            )
            userDao.insertUser(user)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return try {
            val user = userDao.getUserByEmail(email) ?: return null
            val hashedPassword = hashPassword(password)
            if (user.password == hashedPassword) {
                user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun userExists(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}


