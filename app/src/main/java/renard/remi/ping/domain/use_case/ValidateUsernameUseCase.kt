package renard.remi.ping.domain.use_case

class ValidateUsernameUseCase {
    fun execute(username: String?) = username?.isNotBlank() == true && username.length > 2
}