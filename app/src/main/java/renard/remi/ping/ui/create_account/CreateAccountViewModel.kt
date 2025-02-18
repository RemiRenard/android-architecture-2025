package renard.remi.ping.ui.create_account

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
import renard.remi.ping.domain.use_case.CreateAccountUseCase
import renard.remi.ping.domain.use_case.ValidatePasswordUseCase
import renard.remi.ping.domain.use_case.ValidateUsernameUseCase
import renard.remi.ping.extension.UiText
import renard.remi.ping.extension.asUiText
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CreateAccountState> =
        MutableStateFlow(CreateAccountState())
    val state: StateFlow<CreateAccountState>
        get() = _state.asStateFlow()

    private val _eventChannel = Channel<CreateAccountEventFromVm>()
    val events = _eventChannel.receiveAsFlow()

    fun onEvent(event: CreateAccountEventFromUI) {
        when (event) {
            is CreateAccountEventFromUI.ChangeUsername -> _state.update {
                it.copy(
                    username = event.username,
                    usernameError = null
                )
            }

            is CreateAccountEventFromUI.ChangePassword -> _state.update {
                it.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is CreateAccountEventFromUI.ChangeConfirmPassword -> _state.update {
                it.copy(
                    confirmPassword = event.confirmPassword,
                    confirmPasswordError = null
                )
            }

            is CreateAccountEventFromUI.SubmitAccountCreation -> createAccount()
            is CreateAccountEventFromUI.ShowPasswordClicked -> _state.update {
                it.copy(isPasswordVisible = !_state.value.isPasswordVisible)
            }

            is CreateAccountEventFromUI.ShowConfirmPasswordClicked -> _state.update {
                it.copy(isConfirmPasswordVisible = !_state.value.isConfirmPasswordVisible)
            }
        }
    }

    private fun validateUsername(): Boolean {
        val isValid = validateUsernameUseCase.execute(_state.value.username)
        _state.update {
            it.copy(
                usernameError = if (!isValid) {
                    UiText.StringResource(R.string.create_account_field_username_error_msg)
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
                    UiText.StringResource(R.string.create_account_field_password_error_msg)
                } else {
                    null
                }
            )
        }
        return isValid
    }


    private fun validateConfirmPassword(): Boolean {
        val isValid = _state.value.password == _state.value.confirmPassword
        _state.update {
            it.copy(
                confirmPasswordError = if (!isValid) {
                    UiText.StringResource(R.string.create_account_field_confirm_field_password_error_msg)
                } else {
                    null
                }
            )
        }
        return isValid
    }

    private fun createAccount() {
        val isUsernameValid = validateUsername()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        if (!isUsernameValid || !isPasswordValid || !isConfirmPasswordValid) {
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result =
                createAccountUseCase.execute(_state.value.username, _state.value.password)) {
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventChannel.send(CreateAccountEventFromVm.Error(result.error.asUiText()))
                }

                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventChannel.send(
                        CreateAccountEventFromVm.CreateAccountSuccess(
                            accessToken = result.data.accessToken ?: "",
                            userId = result.data.user.id ?: ""
                        )
                    )
                }
            }
        }
    }
}

sealed interface CreateAccountEventFromUI {
    data class ChangeUsername(val username: String) : CreateAccountEventFromUI
    data class ChangePassword(val password: String) : CreateAccountEventFromUI
    data class ChangeConfirmPassword(val confirmPassword: String) : CreateAccountEventFromUI
    data object SubmitAccountCreation : CreateAccountEventFromUI
    data object ShowPasswordClicked : CreateAccountEventFromUI
    data object ShowConfirmPasswordClicked : CreateAccountEventFromUI
}

sealed interface CreateAccountEventFromVm {
    data class Error(val errorMessage: UiText) : CreateAccountEventFromVm
    data class CreateAccountSuccess(val accessToken: String, val userId: String) :
        CreateAccountEventFromVm
}
