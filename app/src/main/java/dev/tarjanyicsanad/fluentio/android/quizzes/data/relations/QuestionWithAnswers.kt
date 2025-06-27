package dev.tarjanyicsanad.fluentio.android.quizzes.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.AnswerOptionEntity
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuestionEntity

data class QuestionWithAnswers(
    @Embedded val question: QuestionEntity,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val answers: List<AnswerOptionEntity>
)
