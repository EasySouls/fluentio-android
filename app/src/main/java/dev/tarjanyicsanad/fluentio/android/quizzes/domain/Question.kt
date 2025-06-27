package dev.tarjanyicsanad.fluentio.android.quizzes.domain

import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuestionEntity

data class Question(
    val questionId: Int,
    val quizId: Int,
    val text: String,
    val answers: List<Answer>
)

fun QuestionEntity.toModel(): Question {
    return Question(
        questionId,
        quizId,
        text,
        emptyList()
    )
}