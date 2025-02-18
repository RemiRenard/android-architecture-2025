package renard.remi.ping.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import renard.remi.ping.data.db.dbo.UserDbo

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbo)

    @Delete
    suspend fun deleteUser(user: UserDbo)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Query("SELECT * FROM users WHERE remoteId =:userId")
    fun findUserById(userId: String): Flow<List<UserDbo>>
}