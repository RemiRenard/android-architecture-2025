package renard.remi.ping.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import renard.remi.ping.R
import renard.remi.ping.domain.model.Result
import renard.remi.ping.domain.use_case.LoginUseCase
import renard.remi.ping.domain.use_case.ValidatePasswordUseCase
import renard.remi.ping.domain.use_case.ValidateUsernameUseCase
import renard.remi.ping.extension.UiText
import renard.remi.ping.extension.asUiText
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    private val _eventChannel = Channel<LoginEventFromVm>()
    val events = _eventChannel.receiveAsFlow()

    fun onEvent(event: LoginEventFromUI) {
        when (event) {
            is LoginEventFromUI.ShowPasswordClicked -> _state.update {
                it.copy(isPasswordVisible = !_state.value.isPasswordVisible)
            }

            is LoginEventFromUI.ChangeUsername -> _state.update {
                it.copy(
                    username = event.username,
                    usernameError = null
                )
            }

            is LoginEventFromUI.ChangePassword -> _state.update {
                it.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is LoginEventFromUI.SubmitLogin -> login()
        }
    }

    private fun validateUsername(): Boolean {
        val isValid = validateUsernameUseCase.execute(_state.value.username)
        _state.update {
            it.copy(
                usernameError = if (!isValid) {
                    UiText.StringResource(R.string.login_field_username_error_msg)
                } else {
                    null
                }
            )
        }
        return isValid
    }

    private fun validatePassword(): Boolean {
        val isValid = validatePasswordUseCase.execute(_state.value.password)
        _state.update {
            it.copy(
                passwordError = if (!isValid) {
                    UiText.StringResource(R.string.login_field_password_error_msg)
                } else {
                    null
                }
            )
        }
        return isValid
    }

    private fun login() {
        val isUsernameValid = validateUsername()
        val isPasswordValid = validatePassword()

        if (!isUsernameValid || !isPasswordValid) {
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = loginUseCase.execute(_state.value.username, _state.value.password)) {
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventChannel.send(LoginEventFromVm.Error(result.error.asUiText()))
                }

                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventChannel.send(
                        LoginEventFromVm.LoginSuccess(
                            accessToken = result.data.accessToken ?: "",
                            userId = result.data.user.id ?: ""
                        )
                    )
                }
            }
        }
    }
}

sealed interface LoginEventFromUI {
    data class ChangeUsername(val username: String) : LoginEventFromUI
    data class ChangePassword(val password: String) : LoginEventFromUI
    data object SubmitLogin : LoginEventFromUI
    data object ShowPasswordClicked : LoginEventFromUI
}

sealed interface LoginEventFromVm {
    data class Error(val errorMessage: UiText) : LoginEventFromVm
    data class LoginSuccess(val accessToken: String, val userId: String) : LoginEventFromVm
}
