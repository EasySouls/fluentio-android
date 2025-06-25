package dev.tarjanyicsanad.fluentio.android.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dev.tarjanyicsanad.fluentio.android.auth.domain.AuthUser
import dev.tarjanyicsanad.fluentio.android.auth.domain.User
import dev.tarjanyicsanad.fluentio.android.auth.domain.toFirestoreUser
import dev.tarjanyicsanad.fluentio.android.core.Resource
import dev.tarjanyicsanad.fluentio.android.di.AppDispatcher
import dev.tarjanyicsanad.fluentio.android.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthService {

    override val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid

    override val hasUser: Boolean
        get() = firebaseAuth.currentUser != null

    override val currentAuthUser: Flow<Resource<AuthUser?>>
        get() = callbackFlow {
            this.trySend(Resource.Loading)

            val listener = FirebaseAuth.AuthStateListener { auth ->
                val user = auth.currentUser?.let {
                    AuthUser(
                        id = it.uid,
                        email = it.email,
                        name = it.displayName,
                        profilePictureUrl = it.photoUrl?.toString()
                    )
                }
                trySend(Resource.Success(user))
            }

            firebaseAuth.addAuthStateListener(listener)

            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }.catch {
            Timber.e(it)
            emit(Resource.Error("An error happened while getting current user"))
        }

    @OptIn(ExperimentalTime::class)
    override suspend fun signUp(
        email: String,
        password: String,
        firstName: String?,
        lastName: String?
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val authUser = result.user
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(
                        if (firstName != null && lastName != null)
                            "$firstName $lastName"
                        else authUser?.email?.substringBefore('@')
                    )
                    .build()

                authUser?.updateProfile(profileChangeRequest)?.addOnSuccessListener { _ ->

                    // Launch a coroutine so we can access datastore and it is more efficient
                    CoroutineScope(ioDispatcher + SupervisorJob()).launch {

                        // Create a new user document in Firestore
                        val user = User(
                            email = authUser.email.toString(),
                            name = authUser.displayName ?: "New User",
                            authId = authUser.uid,
                            profilePictureUrl = if (authUser.photoUrl != null) {
                                authUser.photoUrl.toString()
                            } else {
                                null
                            },
                        )

                        userCollection().document(authUser.uid).set(user.toFirestoreUser()).await()

                        Timber.tag(TAG)
                            .d("User signed up successfully! ${authUser.displayName}")
                    }
                }

            }.addOnFailureListener { exception ->
                Timber.tag(TAG).e(exception)
                throw exception
            }.await()
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount() {
        firebaseAuth.currentUser?.delete()?.await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    private fun userCollection() = firestore.collection(USER_COLLECTION)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val TAG = "FirebaseAuthService"
    }
}