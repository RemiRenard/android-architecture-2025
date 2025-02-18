package renard.remi.ping.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import renard.remi.ping.data.db.dao.UserDao
import renard.remi.ping.data.db.dbo.UserDbo

@Database(entities = [UserDbo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}