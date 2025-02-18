package renard.remi.ping.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
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
import renard.remi.ping.domain.model.User
import renard.remi.ping.ui.component.AppButton

@Serializable
object HomeScreenRoute

@Composable
fun HomeScreen(
    onPermissionNotificationGranted: (() -> Unit)? = null,
    onLogoutClicked: (() -> Unit)? = null,
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

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome ${state?.value?.user?.username}")
            Spacer(Modifier.size(12.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth(0.5F),
                onClick = { onLogoutClicked?.invoke() },
                isLoading = state?.value?.isLoading == true,
                isEnabled = state?.value?.isLoading != true,
                textBtn = "Logout"
            )
        }
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
