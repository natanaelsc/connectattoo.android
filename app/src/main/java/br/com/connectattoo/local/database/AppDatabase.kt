package br.com.connectattoo.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.connectattoo.local.database.dao.TattooClientProfileDao
import br.com.connectattoo.local.database.entitys.TattooClientProfileConverters
import br.com.connectattoo.local.database.entitys.TattooClientProfileEntity
import br.com.connectattoo.local.database.entitys.TagEntity

@Database(entities = [TattooClientProfileEntity::class, TagEntity::class], version = 1, exportSchema = false)
@TypeConverters(TattooClientProfileConverters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun TattooClientProfileDao() : TattooClientProfileDao
    companion object {

        private const val DATABASE_NAME: String = "connectattoo-database"

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
