package renard.remi.ping.ui.settings

import renard.remi.ping.ui.theme.Palette

data class SettingsState(
    val isLoading : Boolean = false,
    val paletteSelected: Palette? = null,
    val useDynamicsColors: Boolean = true,
    val isInDarkMode: Boolean = true,
    val languageSelected: String? = null,
    val languagesAvailable: List<String> = listOf()
)