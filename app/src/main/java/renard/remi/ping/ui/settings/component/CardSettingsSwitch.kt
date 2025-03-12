package renard.remi.ping.ui.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun CardSettingsSwitch(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    isForDarkMode: Boolean = false,
    onCheckedChanged: (isChecked: Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .alpha(0.8F)
            .height(60.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            if (isForDarkMode) {
                SwitchDarkMode(
                    checked = isChecked,
                    onCheckedChange = onCheckedChanged,
                )
            } else {
                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChanged,
                )
            }
        }
    }
}