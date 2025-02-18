package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.UserRepository

data class UpdateFcmTokenUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun execute() = userRepository.updateRemoteFcmToken()
}