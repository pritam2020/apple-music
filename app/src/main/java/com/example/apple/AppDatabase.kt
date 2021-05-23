package com.example.apple

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [entity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}