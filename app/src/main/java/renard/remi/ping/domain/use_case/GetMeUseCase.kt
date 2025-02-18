package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.UserRepository

data class GetMeUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun execute(forceRefresh: Boolean) = userRepository.getMe(forceRefresh = forceRefresh)
}