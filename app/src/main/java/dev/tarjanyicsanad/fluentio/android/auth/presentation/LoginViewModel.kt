package dev.tarjanyicsanad.fluentio.android.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tarjanyicsanad.fluentio.android.auth.data.AuthService
import dev.tarjanyicsanad.fluentio.android.core.Resource
import dev.tarjanyicsanad.fluentio.android.ui.common.NavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = true,
    val email: String = "",
    val password: String = "",
    val error: String? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
//    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            authService.currentAuthUser
                .collect { user ->
                    when (user) {
                        is Resource.Loading -> {
                            loginState = loginState.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            loginState = loginState.copy(isLoading = false)
                            if (user.data != null) {
                                _navigationEvents.send(NavigationEvent.NavigateToHome)
                            }
                        }
                        is Resource.Error -> {
                            loginState = loginState.copy(error = user.message)
                        }
                    }
                }
        }
    }

    fun onEmailChange(input: String) {
        loginState = loginState.copy(email = input, error = null)
    }

    fun onPasswordChange(input: String) {
        loginState = loginState.copy(password = input, error = null)
    }

    fun onLogin() {
        viewModelScope.launch {
            try {
                authService.signIn(
                    email = loginState.email,
                    password = loginState.password
                )
//                analyticsLogger.logEvent(AnalyticsEvent(
//                    type =  FirebaseAnalytics.Event.LOGIN,
//                    extras = listOf(Param(
//                        FirebaseAnalytics.Param.METHOD,
//                        "email"
//                    ))
//                ))
            } catch (e: Exception) {
                loginState = loginState.copy(error = e.message)
            }
        }
    }

    fun onGoogleSignIn() {
//        viewModelScope.launch {
//            try {
//                authService.signInWithGoogle()
//                val user = (authService.currentAuthUser.firstOrNull { it is Resource.Success } as Resource.Success).data
//                if (user == null) {
//                    loginState = loginState.copy(
//                        error = "Nem található Google-fiók az eszközön. Adj hozzá egyet, vagy lépj be email címmel!"
//                    )
//                    return@launch
//                }
//                analyticsLogger.logEvent(
//                    AnalyticsEvent(
//                        type = FirebaseAnalytics.Event.LOGIN,
//                        extras = listOf(
//                            Param(
//                                FirebaseAnalytics.Param.METHOD,
//                                "google"
//                            )
//                        )
//                    )
//                )
//            } catch (_: GetCredentialException) {
//                // It might be better if the repository threw an exception and catch it,
//                // rather than finding out about it after the getCurrentUser call
//                loginState = loginState.copy(error = "No Google account found")
//            } catch (e: Exception) {
//                Timber.e(e)
//                loginState = loginState.copy(error = e.message)
//            }
//            // We don't know if it's the first time someone signs in with their Google account
////            userRepository.createUser(
////                User(
////                    id = user.id,
////                    email = user.email.toString(),
////                    name = user.name.toString(),
////                    authId = user.id,
////                )
////            )
//        }
    }
}