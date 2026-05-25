package com.rocketseat.RRM.tabelanutricional.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.RRM.tabelanutricional.data.datasource.local.AuthDataStore
import com.rocketseat.RRM.tabelanutricional.domain.usecase.LoginUseCase
import com.rocketseat.RRM.tabelanutricional.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUIState())
    val state: StateFlow<AuthUIState> = _state.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            authDataStore.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    authDataStore.loggedInUserEmail.collect { email ->
                        _state.update {
                            it.copy(
                                isLoggedIn = true,
                                currentUser = email
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            when (event) {
                is AuthEvent.Login -> handleLogin(event.email, event.password, event.rememberMe)
                is AuthEvent.Register -> handleRegister(event.username, event.email, event.password)
                is AuthEvent.Logout -> handleLogout()
                is AuthEvent.ClearError -> clearError()
                is AuthEvent.ToggleShowPassword -> toggleShowPassword()
                is AuthEvent.ToggleShowRegisterPassword -> toggleShowRegisterPassword()
            }
        }
    }

    private suspend fun handleLogin(email: String, password: String, rememberMe: Boolean) {
        if (email.isBlank() || password.isBlank()) {
            _state.update { it.copy(errorMessage = "Email e senha são obrigatórios") }
            return
        }

        _state.update { it.copy(isLoading = true) }

        try {
            val user = loginUseCase(email, password)
            if (user != null) {
                authDataStore.setLoggedInUser(email, rememberMe)
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        currentUser = email,
                        errorMessage = null
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Email ou senha inválidos"
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Erro ao fazer login: ${e.message}"
                )
            }
        }
    }

    private suspend fun handleRegister(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _state.update { it.copy(errorMessage = "Todos os campos são obrigatórios") }
            return
        }

        if (password.length < 6) {
            _state.update { it.copy(errorMessage = "Senha deve ter pelo menos 6 caracteres") }
            return
        }

        _state.update { it.copy(isLoading = true) }

        try {
            val registered = registerUseCase(username, email, password)
            if (registered) {
                authDataStore.setLoggedInUser(email, false)
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        currentUser = email,
                        errorMessage = null,
                        isRegistering = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Email já cadastrado ou dados inválidos"
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Erro ao criar conta: ${e.message}"
                )
            }
        }
    }

    private suspend fun handleLogout() {
        authDataStore.logout()
        _state.update {
            it.copy(
                isLoggedIn = false,
                currentUser = null,
                errorMessage = null
            )
        }
    }

    private fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }

    private fun toggleShowPassword() {
        _state.update { it.copy(showPassword = !it.showPassword) }
    }

    private fun toggleShowRegisterPassword() {
        _state.update { it.copy(showRegisterPassword = !it.showRegisterPassword) }
    }

    fun toggleRegisterMode() {
        _state.update { it.copy(isRegistering = !it.isRegistering) }
    }
}

