package com.rocketseat.RRM.tabelanutricional.ui.screen.auth

sealed class AuthEvent {
    data class Login(val email: String, val password: String, val rememberMe: Boolean) : AuthEvent()
    data class Register(val username: String, val email: String, val password: String) : AuthEvent()
    object Logout : AuthEvent()
    object ClearError : AuthEvent()
    object ToggleShowPassword : AuthEvent()
    object ToggleShowRegisterPassword : AuthEvent()
}

