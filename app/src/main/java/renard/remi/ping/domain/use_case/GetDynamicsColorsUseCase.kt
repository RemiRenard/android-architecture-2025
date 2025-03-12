package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.SettingsRepository


data class GetDynamicsColorsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute() = settingsRepository.getDynamicsColors()
}