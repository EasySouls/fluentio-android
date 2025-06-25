package dev.tarjanyicsanad.fluentio.android.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.tarjanyicsanad.fluentio.android.ui.common.AutofillTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToSignUp: () -> Unit,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    onGoogleSignIn: () -> Unit = {},
    loginState: LoginState,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val email = loginState.email
            val password = loginState.password
            val error = loginState.error
            val isLoading = loginState.isLoading

            val focusManager = LocalFocusManager.current
            val localeSoftwareKeyboardController = LocalSoftwareKeyboardController.current

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                var passwordHidden by rememberSaveable {
                    mutableStateOf(true)
                }

                Text("FamilyQuests", style = MaterialTheme.typography.headlineLarge)
                Text(
//                    text = stringResource(R.string.login),
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium
                )

                AutofillTextField(
                    value = email,
                    onValueChange = { onEmailChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    label = {
                        Text(
//                            text = stringResource(R.string.email),
                            text = "Email",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(32.dp),
                    autofillHint = AutofillType.EmailAddress,
                    isOutlined = true
                )

                AutofillTextField(
                    value = password,
                    onValueChange = { onPasswordChange(it) },
                    visualTransformation =  if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            localeSoftwareKeyboardController?.hide()
                            onLogin()
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordHidden = !passwordHidden
                        }) {
                            val icon = if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                            val description = if (passwordHidden) stringResource(R.string.show_password) else stringResource(R.string.hide_password)
                            val description = if (passwordHidden) "Show password" else "Hide password"
                            Icon(
                                imageVector = icon,
                                contentDescription = description
                            )
                        }
                    },
                    label = {
                        Text(
//                            text = stringResource(R.string.password),
                            text = "Password",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    shape = RoundedCornerShape(32.dp),
                    autofillHint = AutofillType.Password,
                    isOutlined = true
                )

                OutlinedButton(
                    onClick = {
                        localeSoftwareKeyboardController?.hide()
                        onLogin()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
//                    Text(text = stringResource(R.string.login))
                    Text("Login")
                }

                Button(
                    onClick = {
                        localeSoftwareKeyboardController?.hide()
                        onGoogleSignIn()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
//                        text = stringResource(R.string.sign_in_with_google),
                        text = "Sign in with Google",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.GppGood,
                        contentDescription = "Google Icon",
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navigateToSignUp()
                    },
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
//                    Text(text = stringResource(R.string.sign_up))
                    Text("Sign Up")
                }

                if (error !== null) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}