package dev.tarjanyicsanad.fluentio.android.auth.presentation

sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
}