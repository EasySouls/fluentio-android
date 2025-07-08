package dev.tarjanyicsanad.fluentio.android.quizzes.domain

import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.AnswerOptionEntity

data class OptionAnswer(
    val answerOptionId: Int,
    val questionId: Int,
    val text: String,
    val isCorrect: Boolean
)

data class FreeTextAnswer(
    val answerId: Int,
    val questionId: Int,
    val expectedAnswer: String
)

fun AnswerOptionEntity.toModel(): OptionAnswer {
    return OptionAnswer(
        answerOptionId,
        questionId,
        text,
        isCorrect
    )
}

fun FreeTextAnswer.toModel(): FreeTextAnswer {
    return FreeTextAnswer(
        answerId,
        questionId,
        expectedAnswer
    )
}