package dev.tarjanyicsanad.fluentio.android.ui.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AutofillTextField(
    value: String,
    onValueChange: (String) -> Unit,
    autofillHint: AutofillType,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable () -> Unit = {},
    label: @Composable () -> Unit = {},
    shape: Shape = TextFieldDefaults.shape,
    isOutlined: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    val autofill = LocalAutofill.current

    val autofillNode = remember {
        AutofillNode(
            autofillTypes = listOf(autofillHint),
            onFill = { onValueChange(it) }
        )
    }

    if (isOutlined) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    autofill?.let {
                        autofillNode.boundingBox = coordinates.boundsInWindow()
                        it.requestAutofillForNode(autofillNode)
                    }
                },
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
            label = label,
            shape = shape,
            maxLines = if (singleLine) 1 else maxLines,
            colors = colors
        )
    } else {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    autofill?.let {
                        autofillNode.boundingBox = coordinates.boundsInWindow()
                        it.requestAutofillForNode(autofillNode)
                    }
                },
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
            label = label,
            shape = shape,
            maxLines = if (singleLine) 1 else maxLines,
            colors = colors
        )
    }
}