package renard.remi.ping.domain.use_case

import renard.remi.ping.domain.repository.AuthRepository

data class CreateAccountUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(username: String, password: String) =
        authRepository.createAccount(username = username, password = password)
}