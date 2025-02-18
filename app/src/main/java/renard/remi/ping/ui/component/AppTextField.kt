package renard.remi.ping.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier,
    value: String,
    label: String,
    isError: Boolean,
    isPassword: Boolean = false,
    isAllCaps: Boolean = false,
    isTextVisible: Boolean = true,
    errorMessage: String?,
    minLines: Int = 1,
    maxLines: Int = 10,
    maxCharacters: Int? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    onValueChange: (String) -> Unit,
    onNext: (() -> Unit)? = null,
    onDone: (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            onValueChange = {
                if (maxCharacters == null || it.length <= maxCharacters) {
                    onValueChange.invoke(it)
                }
            },
            isError = isError,
            modifier = modifier.fillMaxWidth(),
            label = {
                Text(text = label)
            },
            visualTransformation = if (isPassword && !isTextVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) {
                    KeyboardType.Password
                } else {
                    KeyboardType.Text
                },
                imeAction = if (onDone != null) {
                    ImeAction.Done
                } else if (onNext != null) {
                    ImeAction.Next
                } else {
                    ImeAction.Default
                },
                capitalization = if (isAllCaps) {
                    KeyboardCapitalization.Characters
                } else {
                    KeyboardCapitalization.None
                }
            ),
            shape = RoundedCornerShape(25.dp),
            keyboardActions = KeyboardActions(
                onDone = { onDone?.invoke() },
                onNext = { onNext?.invoke() }
            ),
            minLines = minLines,
            maxLines = maxLines
        )
        Row(
            modifier = modifier.padding(end = 2.dp, top = 1.dp),
            horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isError) {
                Text(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
            }
            if (maxCharacters != null) {
                Text(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    text = "${value.length} / $maxCharacters",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppTextFieldPreview() {
    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        value = "value",
        label = "label",
        isError = true,
        maxCharacters = 13,
        trailingIcon = {
            Icon(
                Icons.Default.Person,
                contentDescription = ""
            )
        },
        errorMessage = "Error message",
        onValueChange = {}
    )
}
