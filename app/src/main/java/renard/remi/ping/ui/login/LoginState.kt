package renard.remi.ping.ui.login

import renard.remi.ping.extension.UiText

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val usernameError: UiText? = null,
    val passwordError: UiText? = null,
    val isPasswordVisible: Boolean = false
)