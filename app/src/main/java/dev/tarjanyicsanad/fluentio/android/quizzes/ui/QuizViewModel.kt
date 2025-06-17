package dev.tarjanyicsanad.fluentio.android.quizzes.ui

import androidx.lifecycle.ViewModel
import dev.tarjanyicsanad.fluentio.android.quizzes.data.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QuizUiState(
    val isLoading: Boolean = false,
    val quiz: Quiz? = null,
)

class QuizViewModel(
    quizId: Int,
) : ViewModel() {

    private val _quizState = MutableStateFlow(
        QuizUiState(
            quiz = Quiz(
                id = quizId,
                title = "Test",
                description = "Test",
                questions = emptyList()
            )
        )
    )
    val quizState = _quizState.asStateFlow()
}