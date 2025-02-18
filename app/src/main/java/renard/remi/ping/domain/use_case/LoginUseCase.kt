package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.AuthRepository

data class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(username: String, password: String) =
        authRepository.login(username = username, password = password)
}