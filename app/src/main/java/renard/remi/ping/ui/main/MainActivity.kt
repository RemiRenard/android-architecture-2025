package renard.remi.ping.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import renard.remi.ping.ui.create_account.CreateAccountEventFromUI
import renard.remi.ping.ui.create_account.CreateAccountScreen
import renard.remi.ping.ui.create_account.CreateAccountScreenRoute
import renard.remi.ping.ui.create_account.CreateAccountViewModel
import renard.remi.ping.ui.home.HomeEventFromUI
import renard.remi.ping.ui.home.HomeScreen
import renard.remi.ping.ui.home.HomeScreenRoute
import renard.remi.ping.ui.home.HomeViewModel
import renard.remi.ping.ui.login.LoginEventFromUI
import renard.remi.ping.ui.login.LoginScreen
import renard.remi.ping.ui.login.LoginScreenRoute
import renard.remi.ping.ui.login.LoginViewModel
import renard.remi.ping.ui.theme.PingTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PingTheme {
                val navController = rememberNavController()
                val mainViewModel = hiltViewModel<MainViewModel>()
                val state = mainViewModel.state.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = if (state.value.isConnected == true) HomeScreenRoute else LoginScreenRoute
                ) {
                    composable<CreateAccountScreenRoute> {
                        val viewModel = hiltViewModel<CreateAccountViewModel>()
                        CreateAccountScreen(
                            onUsernameChanged = { viewModel.onEvent(CreateAccountEventFromUI.ChangeUsername(it)) },
                            onPasswordChanged = { viewModel.onEvent(CreateAccountEventFromUI.ChangePassword(it)) },
                            onConfirmPasswordChanged = { viewModel.onEvent(CreateAccountEventFromUI.ChangeConfirmPassword(it)) },
                            onShowPasswordClicked = { viewModel.onEvent(CreateAccountEventFromUI.ShowPasswordClicked) },
                            onShowConfirmPasswordClicked = { viewModel.onEvent(CreateAccountEventFromUI.ShowConfirmPasswordClicked) },
                            onSubmit = { viewModel.onEvent(CreateAccountEventFromUI.SubmitAccountCreation) },
                            onLoginClicked = { navController.navigate(LoginScreenRoute) },
                            events = viewModel.events,
                            state = viewModel.state.collectAsStateWithLifecycle()
                        )
                    }
                    composable<LoginScreenRoute> {
                        val viewModel = hiltViewModel<LoginViewModel>()
                        LoginScreen(
                            onUsernameChanged = { viewModel.onEvent(LoginEventFromUI.ChangeUsername(it)) },
                            onPasswordChanged = { viewModel.onEvent(LoginEventFromUI.ChangePassword(it)) },
                            onShowPasswordClicked = { viewModel.onEvent(LoginEventFromUI.ShowPasswordClicked) },
                            onSubmit = { viewModel.onEvent(LoginEventFromUI.SubmitLogin) },
                            onRegisterClicked = { navController.navigate(CreateAccountScreenRoute) },
                            events = viewModel.events,
                            state = viewModel.state.collectAsStateWithLifecycle()
                        )
                    }
                    composable<HomeScreenRoute> {
                        val viewModel = hiltViewModel<HomeViewModel>()
                        HomeScreen(
                            onPermissionNotificationGranted = { viewModel.onEvent(HomeEventFromUI.PermissionsPushGranted) },
                            onLogoutClicked = { viewModel.onEvent(HomeEventFromUI.Logout) },
                            events = viewModel.events,
                            state = viewModel.state.collectAsStateWithLifecycle()
                        )
                    }
                }
            }
        }
    }
}

