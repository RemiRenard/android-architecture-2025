package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.SettingsRepository

data class UseDynamicsColorsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(shouldUseDynamicsColors: Boolean) =
        settingsRepository.useDynamicsColors(shouldUse = shouldUseDynamicsColors)
}