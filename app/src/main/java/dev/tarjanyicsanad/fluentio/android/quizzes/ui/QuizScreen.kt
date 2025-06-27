package dev.tarjanyicsanad.fluentio.android.quizzes.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuizScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    quizState: QuizUiState
) {
    if (quizState.isLoading) {
        Text("Loading...")
    } else if (quizState.quiz == null) {
        Text("No quiz found")
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                Row {
                    Button(onClick = { onNavigateBack() }) {
                        Text("Back")
                    }
                    Text("Note ${quizState.quiz!!.title}")
                }
            }
            items(100) {
                Text(
                    text = "Item $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}