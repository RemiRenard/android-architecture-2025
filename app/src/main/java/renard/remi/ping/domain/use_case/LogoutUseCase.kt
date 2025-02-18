package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.AuthRepository

data class LogoutUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute() = authRepository.logout()
}