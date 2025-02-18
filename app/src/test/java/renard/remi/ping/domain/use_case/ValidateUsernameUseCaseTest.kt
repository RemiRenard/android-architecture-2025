package renard.remi.ping.domain.use_case

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ValidateUsernameUseCaseTest {

    @Test
    fun `validate username`() {
        val useCase = ValidateUsernameUseCase()
        assertEquals(true, useCase.execute("My username"))
        assertEquals(false, useCase.execute(null))
        assertEquals(false, useCase.execute(""))
        assertEquals(false, useCase.execute("My"))
    }
}