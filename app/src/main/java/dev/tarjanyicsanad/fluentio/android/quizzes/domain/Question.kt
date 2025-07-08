package dev.tarjanyicsanad.fluentio.android.quizzes.domain

import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuestionEntity

data class Question(
    val questionId: Int,
    val quizId: Int,
    val text: String,
    val type: QuestionType,
    val answers: List<OptionAnswer>
)

fun QuestionEntity.toModel(): Question {
    return Question(
        questionId,
        quizId,
        text,
        type,
        emptyList()
    )
}

enum class QuestionType {
    TRUE_FALSE,
    MULTIPLE_CHOICE,
    FREE_TEXT
}