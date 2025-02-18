package renard.remi.ping.ui.create_account

import renard.remi.ping.extension.UiText

data class CreateAccountState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val usernameError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false
)