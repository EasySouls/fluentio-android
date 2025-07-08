package dev.tarjanyicsanad.fluentio.android.quizzes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "free_text_answers")
data class FreeTextAnswerEntity(
    @PrimaryKey val answerId: Int,
    val questionId: Int,
    val expectedAnswer: String
)
