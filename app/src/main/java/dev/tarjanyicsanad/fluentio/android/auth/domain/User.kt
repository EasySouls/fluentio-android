package dev.tarjanyicsanad.fluentio.android.auth.domain

import com.google.firebase.Timestamp
import dev.tarjanyicsanad.fluentio.android.auth.data.model.FirestoreUser
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class User @OptIn(ExperimentalTime::class) constructor(
    val id: String = "",
    val email: String,
    val name: String,
    val authId: String,
    val points: Int = 0,
    val profilePictureUrl: String? = null,
    val createdAt: Instant = Clock.System.now(),
)

@OptIn(ExperimentalTime::class)
fun FirestoreUser.toUser() = User(
    id = id,
    name = name,
    email = email,
    authId = authId,
    createdAt = Instant.fromEpochMilliseconds(createdAt.seconds * 1000)
)

@OptIn(ExperimentalTime::class)
fun User.toFirestoreUser() = FirestoreUser(
    id = id,
    name = name,
    email = email,
    authId = authId,
    createdAt = Timestamp(createdAt.epochSeconds, 0)
)