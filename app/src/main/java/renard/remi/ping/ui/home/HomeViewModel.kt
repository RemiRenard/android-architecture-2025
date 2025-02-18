package renard.remi.ping.ui.home

import android.util.Log
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
import renard.remi.ping.domain.model.Result
import renard.remi.ping.domain.use_case.GetMeUseCase
import renard.remi.ping.domain.use_case.LogoutUseCase
import renard.remi.ping.domain.use_case.UpdateFcmTokenUseCase
import renard.remi.ping.extension.UiText
import renard.remi.ping.extension.asUiText
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    private val _eventChannel = Channel<HomeEventFromVm>()
    val events = _eventChannel.receiveAsFlow()

    init {
        getMe()
    }

    fun onEvent(event: HomeEventFromUI) {
        when (event) {
            is HomeEventFromUI.Logout -> logout()
            is HomeEventFromUI.PermissionsPushGranted -> updateFcmToken()
        }
    }

    private fun getMe() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getMeUseCase.execute(forceRefresh = false)) {
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventChannel.send(HomeEventFromVm.Error(result.error.asUiText()))
                }

                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, user = result.data) }
                }
            }
        }
    }

    private fun updateFcmToken() {
        viewModelScope.launch {
            when (val result = updateFcmTokenUseCase.execute()) {
                is Result.Error -> {
                    Log.e("UpdateFcmToken Error", result.error.toString())
                }

                is Result.Success -> {
                    Log.i("UpdateFcmToken Success", result.data.toString())
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase.execute()
        }
    }
}

sealed interface HomeEventFromUI {
    data object Logout : HomeEventFromUI
    data object PermissionsPushGranted : HomeEventFromUI
}

sealed interface HomeEventFromVm {
    data class Error(val errorMessage: UiText) : HomeEventFromVm
}

