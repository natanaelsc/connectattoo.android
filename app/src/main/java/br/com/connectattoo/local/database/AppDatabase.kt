package br.com.connectattoo.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.connectattoo.local.database.entitys.ClientProfileEntity

@Database(entities = [ClientProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

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
