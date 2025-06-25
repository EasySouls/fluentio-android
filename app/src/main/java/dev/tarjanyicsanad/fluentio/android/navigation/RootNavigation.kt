package dev.tarjanyicsanad.fluentio.android.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import dev.tarjanyicsanad.fluentio.android.auth.presentation.LoginScreen
import dev.tarjanyicsanad.fluentio.android.auth.presentation.LoginViewModel
import dev.tarjanyicsanad.fluentio.android.auth.presentation.SignUpScreen
import dev.tarjanyicsanad.fluentio.android.auth.presentation.SignUpUiState
import dev.tarjanyicsanad.fluentio.android.auth.presentation.SignUpViewModel
import dev.tarjanyicsanad.fluentio.android.quizzes.ui.QuizScreen
import dev.tarjanyicsanad.fluentio.android.ui.HomeScreen
import dev.tarjanyicsanad.fluentio.android.ui.common.NavigationEvent
import dev.tarjanyicsanad.fluentio.android.ui.common.ObserveAsEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data object HomeScreenRoute : NavKey

@Serializable
data class QuizScreenRoute(val id: Int) : NavKey

@Serializable
data object LoginRoute : NavKey

@Serializable
data object SignUpRoute : NavKey

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
                is LoginRoute -> {
                    NavEntry(
                        key = key
                    ) {
                        val viewModel = hiltViewModel<LoginViewModel>()

                        ObserveAsEvents(viewModel.navigationEvents) { event ->
                            when (event) {
                                NavigationEvent.NavigateToHome -> {
//                                    navController.navigate(TabLayoutNavigation) {
//                                        popUpTo(SignUpDestination) {
//                                            inclusive = true
//                                        }
//                                    }
                                    backStack.add(HomeScreenRoute)
                                }
                            }
                        }

                        LoginScreen(
                            modifier = Modifier.fillMaxSize(),
                            onEmailChange = viewModel::onEmailChange,
                            onPasswordChange = viewModel::onPasswordChange,
                            onLogin = viewModel::onLogin,
                            onGoogleSignIn = viewModel::onGoogleSignIn,
                            navigateToSignUp = { backStack.add(SignUpRoute) },
                            loginState = viewModel.loginState
                        )
                    }
                }
                is SignUpRoute -> {
                    NavEntry(
                        key = key
                    ) {
                        val viewModel = hiltViewModel<SignUpViewModel>()

                        val coroutineScope = rememberCoroutineScope()
                        val uiState by viewModel.uiState.collectAsStateWithLifecycle(initialValue = SignUpUiState())

                        ObserveAsEvents(viewModel.navigationEvents) { event ->
                            when (event) {
                                NavigationEvent.NavigateToHome -> {
                                    coroutineScope.launch {
                                        delay(100)
//                                        navController.navigate(TabLayoutNavigation) {
//                                            popUpTo(SignUpDestination) {
//                                                inclusive = true
//                                            }
//                                        }
                                        backStack.add(HomeScreenRoute)
                                    }
                                }
                            }
                        }

                        SignUpScreen(
                            modifier = Modifier.fillMaxSize(),
                            navigateUp = { backStack.removeLastOrNull() },
                            uiState = uiState,
                            onFirstNameChange = viewModel::onFirstNameChange,
                            onLastNameChange = viewModel::onLastNameChange,
                            onEmailChange = viewModel::onEmailChange,
                            onPasswordChange = viewModel::onPasswordChange,
                            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                            onSignUp = viewModel::onSignUp
                        )
                    }
                }
                else -> throw RuntimeException("Unknown key: $key")
            }
        }
    )
}