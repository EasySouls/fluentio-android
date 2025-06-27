package dev.tarjanyicsanad.fluentio.android.quizzes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey val quizId: Int,
    val title: String,
    val authorId: String,
    val description: String?,
    val timestamp: Long
)