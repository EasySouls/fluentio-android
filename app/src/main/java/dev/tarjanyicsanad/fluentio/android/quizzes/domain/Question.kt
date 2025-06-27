package dev.tarjanyicsanad.fluentio.android.quizzes.domain

data class Question(
    val id: Int,
    val question: String,
    val answers: List<Answer>,
    val correctAnswer: Int,
)
