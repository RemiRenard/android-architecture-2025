package renard.remi.ping.data.network.dto

import kotlinx.serialization.Serializable
import renard.remi.ping.domain.model.User

@Serializable
data class UserDto(
    val id: String? = null,
    val username: String? = null,
    val refreshToken: String? = null,
) {
    fun toUser() = User(
        id = id,
        username = username
    )
}