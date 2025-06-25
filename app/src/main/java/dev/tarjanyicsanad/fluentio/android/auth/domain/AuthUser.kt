package dev.tarjanyicsanad.fluentio.android.auth.domain

data class AuthUser(
    val id: String,
    val email: String? = null,
    val name: String? = null,
    val profilePictureUrl: String? = null,
)