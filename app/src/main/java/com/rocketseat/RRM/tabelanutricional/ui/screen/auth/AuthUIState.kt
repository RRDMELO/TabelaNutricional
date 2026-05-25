package com.rocketseat.RRM.tabelanutricional.ui.screen.auth

data class AuthUIState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentUser: String? = null,
    val errorMessage: String? = null,
    val showPassword: Boolean = false,
    val showRegisterPassword: Boolean = false,
    val isRegistering: Boolean = false
)

