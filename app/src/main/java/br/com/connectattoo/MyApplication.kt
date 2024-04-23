package br.com.connectattoo

import android.app.Application
import br.com.connectattoo.local.database.AppDatabase

class MyApplication: Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
    }
}
