package dev.tarjanyicsanad.fluentio.android.quizzes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_options")
data class AnswerOptionEntity(
    @PrimaryKey val answerOptionId: Int,
    val questionId: Int,
    val text: String,
    val isCorrect: Boolean
)
