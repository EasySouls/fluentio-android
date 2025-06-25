package dev.tarjanyicsanad.fluentio.android.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    uiState: SignUpUiState,
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onSignUp: () -> Unit = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back",
                        )
                    }
                },
                title = {
                    Text(
//                        text = stringResource(R.string.sign_up),
                        text = "Sign Up"
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val focusManager = LocalFocusManager.current
            val localeSoftwareKeyboardController = LocalSoftwareKeyboardController.current

            var passwordHidden by rememberSaveable {
                mutableStateOf(true)
            }

            var confirmPasswordHidden by rememberSaveable {
                mutableStateOf(true)
            }

            OutlinedTextField(
                value = uiState.firstName,
                onValueChange = { onFirstNameChange(it) },
                label = {
                    Text(
//                        text = stringResource(R.string.first_name),
                        text = "First name",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )

            OutlinedTextField(
                value = uiState.lastName,
                onValueChange = { onLastNameChange(it) },
                label = {
                    Text(
//                        text = stringResource(R.string.last_name),
                        text = "Last name",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEmailChange(it) },
                label = {
                    Text(
//                        text = stringResource(R.string.email),
                        text = "Email",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onPasswordChange(it) },
                label = {
                    Text(
//                        text = stringResource(R.string.password),
                        text = "Password",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                visualTransformation =  if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordHidden = !passwordHidden
                    }) {
                        val icon = if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                        val description = if (passwordHidden) stringResource(R.string.show_password) else stringResource(R.string.hide_password)
                        val description = if (confirmPasswordHidden) "Show password" else "Hide password"
                        Icon(
                            imageVector = icon,
                            contentDescription = description
                        )
                    }
                },
            )

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { onConfirmPasswordChange(it) },
                label = {
                    Text(
//                        text = stringResource(R.string.confirm_password),
                        text = "Confirm password",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        localeSoftwareKeyboardController?.hide()
                        onSignUp()
                    }
                ),
                visualTransformation =  if (confirmPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordHidden = !confirmPasswordHidden
                    }) {
                        val icon = if (confirmPasswordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                        val description = if (confirmPasswordHidden) stringResource(R.string.show_password) else stringResource(R.string.hide_password)
                        val description = if (confirmPasswordHidden) "Show password" else "Hide password"
                        Icon(
                            imageVector = icon,
                            contentDescription = description
                        )
                    }
                },
            )

            if (uiState.passwordError !== null) {
                Text(
                    text = uiState.passwordError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Button(
                onClick = {
                    localeSoftwareKeyboardController?.hide()
                    onSignUp()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
//                Text(text = stringResource(R.string.sign_up))
                Text("Sign Up")
            }
        }
    }
}