package renard.remi.ping.domain.repository

import renard.remi.ping.domain.model.DataError
import renard.remi.ping.domain.model.Result
import renard.remi.ping.domain.model.User

interface UserRepository {

    suspend fun getMe(forceRefresh: Boolean): Result<User?, DataError>

    suspend fun updateRemoteFcmToken(fcmToken: String? = null): Result<Any, DataError.Network>
}