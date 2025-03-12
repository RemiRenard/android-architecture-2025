package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.SettingsRepository

data class UpdateDarkModeUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(isInDarkMode: Boolean) =
        settingsRepository.setDarkMode(isInDarkMode = isInDarkMode)
}