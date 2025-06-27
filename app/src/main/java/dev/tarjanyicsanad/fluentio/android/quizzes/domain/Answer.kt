package dev.tarjanyicsanad.fluentio.android.quizzes.domain

import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.AnswerOptionEntity

data class Answer(
    val answerOptionId: Int,
    val questionId: Int,
    val text: String,
    val isCorrect: Boolean
)

fun AnswerOptionEntity.toModel(): Answer {
    return Answer(
        answerOptionId,
        questionId,
        text,
        isCorrect
    )
}