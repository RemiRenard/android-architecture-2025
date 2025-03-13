package renard.remi.ping.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import renard.remi.ping.R
import renard.remi.ping.domain.model.BaseRoute
import renard.remi.ping.domain.model.User

@Serializable
object HomeScreenRoute : BaseRoute

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPermissionNotificationGranted: (() -> Unit)? = null,
    events: Flow<HomeEventFromVm>? = null,
    state: State<HomeState>? = null
) {
    val context = LocalContext.current

    val launcherNotificationsPerms = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onPermissionNotificationGranted?.invoke()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcherNotificationsPerms.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            onPermissionNotificationGranted?.invoke()
        }

        events?.collect { event ->
            if (event is HomeEventFromVm.Error) {
                Toast.makeText(
                    context,
                    event.errorMessage.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = context.getString(R.string.home_title, state?.value?.user?.username ?: ""),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val state = MutableStateFlow(
        HomeState(
            isLoading = false,
            user = User(username = "My Username")
        )
    )

    HomeScreen(state = state.asStateFlow().collectAsStateWithLifecycle())
}
