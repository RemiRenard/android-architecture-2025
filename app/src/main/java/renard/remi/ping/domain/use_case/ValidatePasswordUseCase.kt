package renard.remi.ping.domain.use_case

class ValidatePasswordUseCase {
    fun execute(password: String?) =
        password?.isNotBlank() == true
                && password.length > 5
                && password.contains(Regex("[A-Z0-9<\\n]+"))
}