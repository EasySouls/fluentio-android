package dev.tarjanyicsanad.fluentio.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import dev.tarjanyicsanad.fluentio.android.quizzes.ui.QuizScreen
import dev.tarjanyicsanad.fluentio.android.ui.HomeScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data object HomeScreenRoute : NavKey

@Serializable
data class QuizScreenRoute(val id: Int) : NavKey

@Composable
fun RootNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeScreenRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        sceneStrategy = TwoPaneSceneStrategy(),
        entryProvider = { key ->
            when (key) {
                HomeScreenRoute -> {
                    NavEntry(
                        key = key,
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        HomeScreen(
                            onQuizClick = { id ->
                                backStack.add(QuizScreenRoute(id = id))
                            }
                        )
                    }
                }
                is QuizScreenRoute -> {
                    NavEntry(
                        key = key,
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        QuizScreen(
                            viewModel = koinViewModel { parametersOf(key.id) },
                            onNavigateBack = { backStack.removeLastOrNull() }
                        )
                    }
                }
                else -> throw RuntimeException("Unknown key: $key")
            }
        }
    )
}