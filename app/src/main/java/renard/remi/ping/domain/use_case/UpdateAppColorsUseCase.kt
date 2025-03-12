package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.SettingsRepository
import renard.remi.ping.ui.theme.Palette

data class UpdateAppColorsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(palette: Palette) = settingsRepository.updateAppColors(palette = palette)
}