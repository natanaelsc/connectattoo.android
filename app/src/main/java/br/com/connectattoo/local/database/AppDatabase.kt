package br.com.connectattoo.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.connectattoo.local.database.dao.TattooClientProfileDao
import br.com.connectattoo.local.database.entity.TagEntity
import br.com.connectattoo.local.database.entity.TattooClientProfileConverters
import br.com.connectattoo.local.database.entity.TattooClientProfileEntity
import br.com.connectattoo.util.Constants.DATABASE_NAME

@Database(
    entities = [TattooClientProfileEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TattooClientProfileConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tattooClientProfileDao(): TattooClientProfileDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
    }
}
