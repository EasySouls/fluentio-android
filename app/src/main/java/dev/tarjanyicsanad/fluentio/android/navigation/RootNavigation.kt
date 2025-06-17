package dev.tarjanyicsanad.fluentio.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import dev.tarjanyicsanad.fluentio.android.ui.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen : NavKey

@Serializable
data class QuizScreen(val id: Int) : NavKey

@Composable
fun RootNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                HomeScreen -> {
                    NavEntry(
                        key = key,
                    ) {
                        HomeScreen()
                    }
                }
                is QuizScreen -> {
                    NavEntry(
                        key = key,
                    ) {
                        QuizScreen(id = key.id)
                    }
                }
                else -> throw RuntimeException("Unknown key: $key")
            }
        }
    )
}