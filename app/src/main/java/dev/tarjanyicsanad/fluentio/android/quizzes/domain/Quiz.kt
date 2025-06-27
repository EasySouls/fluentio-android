package dev.tarjanyicsanad.fluentio.android.quizzes.domain

import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuizEntity
import dev.tarjanyicsanad.fluentio.android.quizzes.data.relations.QuizWithQuestions
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Quiz @OptIn(ExperimentalTime::class) constructor(
    val quizId: Int,
    val title: String,
    val description: String?,
    val createdAt: Instant,
    var questions: List<Question>,
)

@OptIn(ExperimentalTime::class)
fun QuizEntity.toModel(): Quiz {
    return Quiz(
        quizId,
        title,
        description,
        Instant.fromEpochMilliseconds(timestamp),
        emptyList()
    )
}

fun QuizWithQuestions.toModel(): Quiz {
    val quiz = quiz.toModel()
    val questions = questions.map { it.toModel() }
    quiz.questions = questions
    return quiz;
}