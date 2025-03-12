package renard.remi.ping.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import renard.remi.ping.domain.use_case.GetDynamicsColorsUseCase
import renard.remi.ping.domain.use_case.GetIsInDarkModeUseCase
import renard.remi.ping.domain.use_case.GetPaletteColorsUseCase
import renard.remi.ping.domain.use_case.LogoutUseCase
import renard.remi.ping.domain.use_case.UpdateAppColorsUseCase
import renard.remi.ping.domain.use_case.UpdateDarkModeUseCase
import renard.remi.ping.domain.use_case.UseDynamicsColorsUseCase
import renard.remi.ping.extension.dataStore
import renard.remi.ping.ui.theme.Palette
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logoutUseCase: LogoutUseCase,
    private val updateAppColorsUseCase: UpdateAppColorsUseCase,
    private val setDarkModeUseCase: UpdateDarkModeUseCase,
    private val useDynamicsColorsUseCase: UseDynamicsColorsUseCase,
    private val getDynamicsColorsUseCase: GetDynamicsColorsUseCase,
    private val getPaletteColorsUseCase: GetPaletteColorsUseCase,
    private val getIsInDarkModeUseCase: GetIsInDarkModeUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState>
        get() = _state.asStateFlow()

    private val _eventChannel = Channel<SettingsEventFromVm>()
    val events = _eventChannel.receiveAsFlow()

    init {
        getColors()
        _state.update {
            it.copy(
                languagesAvailable = listOf("French", "English")
            )
        }
    }

    fun onEvent(event: SettingsEventFromUI) {
        when (event) {
            is SettingsEventFromUI.Logout -> logout()
            is SettingsEventFromUI.OnPaletteChanged -> onPaletteChanged(event.palette)
            is SettingsEventFromUI.UseDynamicsColors -> onDynamicColorsChanged(event.shouldUse)
            is SettingsEventFromUI.OnLanguageChanged -> onLanguageChanged(event.currentLanguage)
            is SettingsEventFromUI.ShouldUseDarkMode -> onUseDarkModeChanged(event.isDark)
        }
    }

    private fun onPaletteChanged(palette: Palette) {
        _state.update {
            it.copy(paletteSelected = palette)
        }
        viewModelScope.launch {
            updateAppColorsUseCase.execute(palette)
        }
    }

    private fun onUseDarkModeChanged(isDark: Boolean) {
        _state.update {
            it.copy(isInDarkMode = isDark)
        }
        viewModelScope.launch {
            setDarkModeUseCase.execute(isInDarkMode = isDark)
        }
    }

    private fun onDynamicColorsChanged(shouldUse: Boolean) {
        _state.update {
            it.copy(useDynamicsColors = shouldUse)
        }
        viewModelScope.launch {
            useDynamicsColorsUseCase.execute(shouldUseDynamicsColors = shouldUse)
        }
    }

    private fun getColors() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    useDynamicsColors = getDynamicsColorsUseCase.execute(),
                    paletteSelected = getPaletteColorsUseCase.execute(),
                    isInDarkMode = getIsInDarkModeUseCase.execute()
                )
            }
        }
    }

    private fun onLanguageChanged(currentLanguage: String) {
        _state.update {
            it.copy(languageSelected = currentLanguage)
        }
        viewModelScope.launch {
            context.dataStore.updateData {
                it.copy(currentLanguage = currentLanguage)
            }
            _eventChannel.send(SettingsEventFromVm.OnLanguageChanged)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase.execute()
        }
    }
}

sealed interface SettingsEventFromUI {
    data class OnPaletteChanged(val palette: Palette) : SettingsEventFromUI
    data class ShouldUseDarkMode(val isDark: Boolean) : SettingsEventFromUI
    data class UseDynamicsColors(val shouldUse: Boolean) : SettingsEventFromUI
    data class OnLanguageChanged(val currentLanguage: String) : SettingsEventFromUI
    data object Logout : SettingsEventFromUI
}

sealed interface SettingsEventFromVm {
    data object OnLanguageChanged : SettingsEventFromVm
}