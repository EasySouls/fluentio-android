package dev.tarjanyicsanad.fluentio.android.auth.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class FirestoreUser(
    @DocumentId val id: String = "",
    val name: String = "",
    val email: String = "",
    val authId: String = "",
    val createdAt: Timestamp = Timestamp.now(),
)