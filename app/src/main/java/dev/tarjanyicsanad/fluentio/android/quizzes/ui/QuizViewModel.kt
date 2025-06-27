package dev.tarjanyicsanad.fluentio.android.quizzes.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tarjanyicsanad.fluentio.android.navigation.QuizScreenRoute
import dev.tarjanyicsanad.fluentio.android.quizzes.data.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class QuizUiState(
    val isLoading: Boolean = false,
    val quiz: Quiz? = null,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val quizIdKey = "quizId"
    private val quizScreenData: QuizScreenRoute = savedStateHandle.toRoute()
    private val quizId = savedStateHandle.getStateFlow(
        key = quizIdKey,
        initialValue = quizScreenData.quizId
    ).value

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