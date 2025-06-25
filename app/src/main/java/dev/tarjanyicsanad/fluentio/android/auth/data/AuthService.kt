package dev.tarjanyicsanad.fluentio.android.auth.data

import dev.tarjanyicsanad.fluentio.android.auth.domain.AuthUser
import dev.tarjanyicsanad.fluentio.android.core.Resource
import kotlinx.coroutines.flow.Flow

/**
 * An interface representing an authentication service.
 * This service provides methods for user authentication, account management,
 * and access to current user information.
 */
interface AuthService {
    val currentUserId: String?
    val hasUser: Boolean
    val currentAuthUser: Flow<Resource<AuthUser?>>

    suspend fun signUp(email: String, password: String, firstName: String? = null, lastName: String? = null)

    suspend fun signIn(email: String, password: String)

    suspend fun sendRecoveryEmail(email: String)

    suspend fun deleteAccount()

    suspend fun signOut()

}