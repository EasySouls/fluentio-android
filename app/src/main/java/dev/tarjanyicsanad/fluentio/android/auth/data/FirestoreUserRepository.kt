package dev.tarjanyicsanad.fluentio.android.auth.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import dev.tarjanyicsanad.fluentio.android.auth.data.model.FirestoreUser
import dev.tarjanyicsanad.fluentio.android.auth.domain.User
import dev.tarjanyicsanad.fluentio.android.auth.domain.toFirestoreUser
import com.google.firebase.firestore.toObject
import dev.tarjanyicsanad.fluentio.android.auth.domain.toUser
import dev.tarjanyicsanad.fluentio.android.core.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirestoreUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authService: AuthService,
) : UserRepository {

    override suspend fun createUser(user: User) {
        userCollection().add(user.toFirestoreUser()).await()
    }

    override suspend fun getUser(id: String): User? {
        return userCollection().document(id).get().await().toObject<FirestoreUser>()?.toUser()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getOwnUser(): Flow<Resource<User?>> = authService.currentAuthUser.flatMapLatest { authUserResource ->
        return@flatMapLatest when (authUserResource) {
            is Resource.Error -> flowOf(Resource.Error("The user is not found"))
            is Resource.Loading -> flowOf(Resource.Loading)
            is Resource.Success -> {
                if (authUserResource.data == null) {
                    return@flatMapLatest flowOf(Resource.Error("The user is not found"))
                }
                val authUser = authUserResource.data
                userCollection().document(authUser.id).snapshots().map { snapshot ->
                    Resource.Success(snapshot.toObject<FirestoreUser>()?.toUser())
                }
            }
        }
    }
        .onStart { emit(Resource.Loading) }
        .catch {
            Timber.tag(TAG).e(it)
            emit(Resource.Error("The user is not found"))
        }

    override fun observeUser(id: String): Flow<User?> {
        return userCollection().document(id).snapshots().map { snapshot ->
            snapshot.toObject<FirestoreUser>()?.toUser()
        }
    }

    override fun updateUser(user: User) {
        userCollection().document(user.id).set(user.toFirestoreUser())
    }

    private fun userCollection() = firestore.collection(USER_COLLECTION)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val TAG = "FirestoreUserRepository"
    }
}