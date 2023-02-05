package com.example.beeruthus.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beeruthus.model.Beer
import com.example.beeruthus.model.Rating

@Database(entities = [Beer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}