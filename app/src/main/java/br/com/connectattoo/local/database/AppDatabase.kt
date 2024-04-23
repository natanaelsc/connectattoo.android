package br.com.connectattoo.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


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
