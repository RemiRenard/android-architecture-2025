package renard.remi.ping.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import renard.remi.ping.R
import renard.remi.ping.extension.dataStore
import renard.remi.ping.extension.removeAllAfterSlash
import renard.remi.ping.ui.component.CustomBottomBar
import renard.remi.ping.ui.component.WebView
import renard.remi.ping.ui.component.WebViewRoute
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
import renard.remi.ping.ui.settings.SettingsEventFromUI
import renard.remi.ping.ui.settings.SettingsScreen
import renard.remi.ping.ui.settings.SettingsScreenRoute
import renard.remi.ping.ui.settings.SettingsViewModel
import renard.remi.ping.ui.theme.PingTheme
import java.util.Locale

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

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route.toString()
                val routeWithBottomBar = listOf(
                    HomeScreenRoute.javaClass.name,
                    WebViewRoute::class.java.name,
                )
                val routeWithBackground = listOf(
                    LoginScreenRoute.javaClass.name,
                    CreateAccountScreenRoute.javaClass.name,
                    HomeScreenRoute.javaClass.name,
                    SettingsScreenRoute.javaClass.name
                )

                Scaffold { paddingValues ->
                    AnimatedVisibility(
                        routeWithBackground.contains(currentRoute.removeAllAfterSlash()),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(36.dp),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Image(
                                painter = painterResource(R.drawable.bg),
                                modifier = Modifier,
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                        }
                    }
                    NavHost(
                        navController = navController,
                        startDestination = if (state.value.isConnected == true) HomeScreenRoute else LoginScreenRoute
                    ) {
                        composable<CreateAccountScreenRoute> {
                            val viewModel = hiltViewModel<CreateAccountViewModel>()
                            CreateAccountScreen(
                                onUsernameChanged = {
                                    viewModel.onEvent(
                                        CreateAccountEventFromUI.ChangeUsername(
                                            it
                                        )
                                    )
                                },
                                onPasswordChanged = {
                                    viewModel.onEvent(
                                        CreateAccountEventFromUI.ChangePassword(
                                            it
                                        )
                                    )
                                },
                                onConfirmPasswordChanged = {
                                    viewModel.onEvent(
                                        CreateAccountEventFromUI.ChangeConfirmPassword(it)
                                    )
                                },
                                onShowPasswordClicked = { viewModel.onEvent(CreateAccountEventFromUI.ShowPasswordClicked) },
                                onShowConfirmPasswordClicked = {
                                    viewModel.onEvent(
                                        CreateAccountEventFromUI.ShowConfirmPasswordClicked
                                    )
                                },
                                onSubmit = { viewModel.onEvent(CreateAccountEventFromUI.SubmitAccountCreation) },
                                onLoginClicked = { navController.navigate(LoginScreenRoute) },
                                events = viewModel.events,
                                state = viewModel.state.collectAsStateWithLifecycle()
                            )
                        }
                        composable<LoginScreenRoute> {
                            val viewModel = hiltViewModel<LoginViewModel>()
                            LoginScreen(
                                onUsernameChanged = {
                                    viewModel.onEvent(
                                        LoginEventFromUI.ChangeUsername(
                                            it
                                        )
                                    )
                                },
                                onPasswordChanged = {
                                    viewModel.onEvent(
                                        LoginEventFromUI.ChangePassword(
                                            it
                                        )
                                    )
                                },
                                onShowPasswordClicked = { viewModel.onEvent(LoginEventFromUI.ShowPasswordClicked) },
                                onSubmit = { viewModel.onEvent(LoginEventFromUI.SubmitLogin) },
                                onRegisterClicked = {
                                    navController.navigate(
                                        CreateAccountScreenRoute
                                    )
                                },
                                events = viewModel.events,
                                state = viewModel.state.collectAsStateWithLifecycle()
                            )
                        }
                        composable<HomeScreenRoute> {
                            val viewModel = hiltViewModel<HomeViewModel>()
                            HomeScreen(
                                modifier = Modifier.padding(paddingValues),
                                onPermissionNotificationGranted = {
                                    viewModel.onEvent(
                                        HomeEventFromUI.PermissionsPushGranted
                                    )
                                },
                                events = viewModel.events,
                                state = viewModel.state.collectAsStateWithLifecycle()
                            )
                        }
                        composable<WebViewRoute> {
                            WebView(
                                modifier = Modifier.padding(paddingValues),
                                url = it.toRoute<WebViewRoute>().url
                            )
                        }
                        composable<SettingsScreenRoute> {
                            val viewModel = hiltViewModel<SettingsViewModel>()
                            SettingsScreen(
                                modifier = Modifier.padding(paddingValues),
                                onLogoutClicked = { viewModel.onEvent(SettingsEventFromUI.Logout) },
                                onDynamicsColorsChanged = {
                                    viewModel.onEvent(SettingsEventFromUI.UseDynamicsColors(it))
                                },
                                shouldUseDarkMode = {
                                    viewModel.onEvent(SettingsEventFromUI.ShouldUseDarkMode(it))
                                },
                                onColorChanged = {
                                    viewModel.onEvent(SettingsEventFromUI.OnPaletteChanged(it))
                                },
                                onLanguageChanged = {
                                    viewModel.onEvent(SettingsEventFromUI.OnLanguageChanged(it))
                                },
                                events = viewModel.events,
                                state = viewModel.state.collectAsStateWithLifecycle()
                            )
                        }
                    }
                    CustomBottomBar(
                        modifier = Modifier.padding(paddingValues),
                        isVisible = routeWithBottomBar.contains(currentRoute.removeAllAfterSlash()),
                        currentRoute = currentRoute,
                        onTabClicked = {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onSettingsClicked = {
                            navController.navigate(SettingsScreenRoute)
                        }
                    )
                }
            }
        }
    }

    override fun attachBaseContext(context: Context) {
        val newConfig = context.resources.configuration
        val currentLanguage = runBlocking { context.dataStore.data.first().currentLanguage }
        currentLanguage?.let {
            // TODO improve this
            newConfig?.setLocale(Locale(it.take(2).lowercase()))
            applyOverrideConfiguration(newConfig)
        }
        super.attachBaseContext(context)
    }
}

