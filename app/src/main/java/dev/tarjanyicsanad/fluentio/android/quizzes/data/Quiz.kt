package dev.tarjanyicsanad.fluentio.android.quizzes.data

data class Quiz(
    val id: Int,
    val title: String,
    val description: String,
    val questions: List<Question>,
)
