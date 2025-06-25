package dev.tarjanyicsanad.fluentio.android.auth.data

import dev.tarjanyicsanad.fluentio.android.auth.domain.User
import dev.tarjanyicsanad.fluentio.android.core.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun createUser(user: User)

    suspend fun getUser(id: String): User?

    fun getOwnUser(): Flow<Resource<User?>>

    fun observeUser(id: String): Flow<User?>

    fun updateUser(user: User)
}