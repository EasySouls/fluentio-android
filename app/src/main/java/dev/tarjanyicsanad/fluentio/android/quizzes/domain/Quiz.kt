package dev.tarjanyicsanad.fluentio.android.quizzes.domain

data class Quiz(
    val id: Int,
    val title: String,
    val description: String,
    val questions: List<Question>,
)
