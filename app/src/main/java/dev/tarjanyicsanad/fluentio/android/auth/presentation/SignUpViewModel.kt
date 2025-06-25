package dev.tarjanyicsanad.fluentio.android.auth.presentation

import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tarjanyicsanad.fluentio.android.auth.data.AuthService
import dev.tarjanyicsanad.fluentio.android.core.Resource
import dev.tarjanyicsanad.fluentio.android.ui.common.NavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val passwordError: String? = null,
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService,
//    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: Flow<SignUpUiState> = _uiState.asStateFlow()

    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    val errorChannel = Channel<String>()

    init {
        viewModelScope.launch {
            authService.currentAuthUser
                .collect { userResource ->
                    if (userResource is Resource.Success && userResource.data != null) {
                        _navigationEvents.send(NavigationEvent.NavigateToHome)
                    }
                }
        }
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null
        )
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            passwordError = null
        )
    }

    fun onFirstNameChange(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun onLastNameChange(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun onSignUp() {
        viewModelScope.launch {
            if (_uiState.value.password != _uiState.value.confirmPassword) {
                _uiState.value = _uiState.value.copy(passwordError = "Passwords do not match")
                return@launch
            }

            try {
                authService.signUp(
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    firstName = _uiState.value.firstName,
                    lastName = _uiState.value.lastName
                )

//                analyticsLogger.logEvent(
//                    AnalyticsEvent(
//                        type = FirebaseAnalytics.Event.SIGN_UP,
//                        extras = listOf(
//                            Param(
//                                FirebaseAnalytics.Param.METHOD,
//                                "email"
//                            )
//                        )
//                    )
//                )
            } catch (_: NoCredentialException) {
                errorChannel.send("Nem található Google-fiók az eszközön!")
            } catch (_: Exception) {
                errorChannel.send("Sikertelen regisztráció!")
            }

        }
    }
}