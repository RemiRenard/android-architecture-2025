package renard.remi.ping.data.network.dto.response

import kotlinx.serialization.Serializable
import renard.remi.ping.data.network.dto.UserDto

@Serializable
data class AuthResponse(
    val accessToken: String? = null,
    val user: UserDto
)
