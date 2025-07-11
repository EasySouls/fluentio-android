package dev.tarjanyicsanad.fluentio.android.quizzes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.tarjanyicsanad.fluentio.android.quizzes.domain.QuestionType

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val questionId: Int,
    val quizId: Int,
    val text: String,
    val type: QuestionType
)
