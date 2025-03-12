package renard.remi.ping.ui.settings.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> AppExposedDropdownMenuBox(
    modifier: Modifier = Modifier,
    label: String,
    data: List<T?>,
    typeToString: (data: T?) -> String,
    onDataChanged: (data: T?) -> Unit?
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .size(60.dp)
            .alpha(0.8F),
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
            readOnly = true,
            value = label,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            border = null
        ) {
            data.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onDataChanged(item)
                        isExpanded = false
                    },
                    text = {
                        Text(
                            text = typeToString(item),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }
    }
}