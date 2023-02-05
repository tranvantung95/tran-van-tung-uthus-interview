package com.example.beeruthus

import android.app.Application
import androidx.room.Room
import com.example.beeruthus.room.AppDatabase

class UthusApp : Application() {
    companion object {
        lateinit var db: AppDatabase  // trong thuc te se dung DI
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "UthusDb"
        ).build()
    }
}
