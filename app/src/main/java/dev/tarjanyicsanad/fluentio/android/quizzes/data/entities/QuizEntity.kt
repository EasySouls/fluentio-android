package dev.tarjanyicsanad.fluentio.android.quizzes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey val quizId: Int,
    val authorId: String,
    val timestamp: Long
)