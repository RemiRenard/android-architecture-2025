package renard.remi.ping.data.db.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import renard.remi.ping.domain.model.User

@Entity(tableName = "users")
data class UserDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId: String,
    val username: String,
    val refreshToken: String,
) {
    fun toUser() =
        User(
            id = remoteId,
            username = username
        )
}