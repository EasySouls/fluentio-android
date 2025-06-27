package dev.tarjanyicsanad.fluentio.android.quizzes.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuestionEntity
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuizEntity

data class QuizWithQuestions(
    @Embedded val quiz: QuizEntity,

    @Relation(
        parentColumn = "quizId",
        entityColumn = "quizId"
    )
    val questions: List<QuestionEntity>
)
