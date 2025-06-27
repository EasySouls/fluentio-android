package dev.tarjanyicsanad.fluentio.android.quizzes.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tarjanyicsanad.fluentio.android.navigation.QuizScreenRoute
import dev.tarjanyicsanad.fluentio.android.quizzes.data.QuizDao
import dev.tarjanyicsanad.fluentio.android.quizzes.domain.Quiz
import dev.tarjanyicsanad.fluentio.android.quizzes.domain.toModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class QuizUiState(
    val isLoading: Boolean = false,
    val quiz: Quiz? = null,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    quizDao: QuizDao
) : ViewModel() {

    private val quizIdKey = "quizId"
    private val quizScreenData: QuizScreenRoute = savedStateHandle.toRoute()
    private val quizId = savedStateHandle.getStateFlow(
        key = quizIdKey,
        initialValue = quizScreenData.quizId
    ).value

    @OptIn(ExperimentalTime::class)
    val quizState: StateFlow<QuizUiState> = quizDao.getQuizWithQuestions(quizId)
        .map { quiz ->
            QuizUiState(false, quiz?.toModel())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuizUiState(true, null)
        )
}