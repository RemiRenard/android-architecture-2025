package renard.remi.ping.domain.repository

import renard.remi.ping.data.network.dto.response.AuthResponse
import renard.remi.ping.domain.model.DataError
import renard.remi.ping.domain.model.Result

interface AuthRepository {

    suspend fun login(
        username: String,
        password: String
    ): Result<AuthResponse, DataError.Network>

    suspend fun createAccount(
        username: String,
        password: String
    ): Result<AuthResponse, DataError.Network>

    suspend fun logout()

    suspend fun getAccessToken(): String?

    suspend fun getCurrentUserId(): String?
}