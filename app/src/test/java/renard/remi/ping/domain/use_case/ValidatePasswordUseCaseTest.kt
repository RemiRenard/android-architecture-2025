package renard.remi.ping.domain.use_case

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class ValidatePasswordUseCaseTest {

    @Test
    fun `validate password`() {
        val useCase = ValidatePasswordUseCase()
        assertEquals(true, useCase.execute("123456"))
        assertEquals(true, useCase.execute("passwordWithUppercase"))
        assertEquals(true, useCase.execute("passwordWithUppercaseAnd123"))
        assertEquals(false, useCase.execute("123"))
        assertEquals(false, useCase.execute("passwordwithoutuppercase"))
    }
}