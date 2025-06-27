package dev.tarjanyicsanad.fluentio.android.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.tarjanyicsanad.fluentio.android.auth.presentation.LoginScreen
import dev.tarjanyicsanad.fluentio.android.auth.presentation.LoginViewModel
import dev.tarjanyicsanad.fluentio.android.auth.presentation.SignUpScreen
import dev.tarjanyicsanad.fluentio.android.auth.presentation.SignUpUiState
import dev.tarjanyicsanad.fluentio.android.auth.presentation.SignUpViewModel
import dev.tarjanyicsanad.fluentio.android.quizzes.ui.QuizScreen
import dev.tarjanyicsanad.fluentio.android.quizzes.ui.QuizViewModel
import dev.tarjanyicsanad.fluentio.android.ui.HomeScreen
import dev.tarjanyicsanad.fluentio.android.ui.common.NavigationEvent
import dev.tarjanyicsanad.fluentio.android.ui.common.ObserveAsEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object SignUpRoute

@Serializable
data object TabLayoutNavigation

@Serializable
data object HomeScreenRoute

@Serializable
data class QuizScreenRoute(val quizId: Int)

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = modifier
    ) {
        composable<LoginRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()

            ObserveAsEvents(viewModel.navigationEvents) { event ->
                when (event) {
                    NavigationEvent.NavigateToHome -> {
                        navController.navigate(TabLayoutNavigation) {
                            popUpTo(SignUpRoute) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLogin = viewModel::onLogin,
                onGoogleSignIn = viewModel::onGoogleSignIn,
                navigateToSignUp = { navController.navigate(SignUpRoute) },
                loginState = viewModel.loginState
            )
        }
        composable<SignUpRoute> {
            val viewModel = hiltViewModel<SignUpViewModel>()

            val coroutineScope = rememberCoroutineScope()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle(initialValue = SignUpUiState())

            ObserveAsEvents(viewModel.navigationEvents) { event ->
                when (event) {
                    NavigationEvent.NavigateToHome -> {
                        coroutineScope.launch {
                            delay(100)
                            navController.navigate(TabLayoutNavigation) {
                                popUpTo(SignUpRoute) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }

            SignUpScreen(
                modifier = Modifier.fillMaxSize(),
                navigateUp = { navController.navigateUp() },
                uiState = uiState,
                onFirstNameChange = viewModel::onFirstNameChange,
                onLastNameChange = viewModel::onLastNameChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                onSignUp = viewModel::onSignUp
            )
        }

        navigation<TabLayoutNavigation>(
            startDestination = HomeScreenRoute
        ) {
            composable<HomeScreenRoute> {
                HomeScreen(
                    onQuizClick = { quizId ->
                        navController.navigate(QuizScreenRoute(quizId = quizId))
                    }
                )
            }

            composable<QuizScreenRoute> {
                val viewModel = hiltViewModel<QuizViewModel>()
                val quizState by viewModel.quizState.collectAsStateWithLifecycle()

                QuizScreen(
                    onNavigateBack = { navController.navigateUp() },
                    quizState = quizState
                )
            }
        }
    }
}