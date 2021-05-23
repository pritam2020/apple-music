package com.example.apple

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM entity")
    suspend fun getAll(): List<entity>

    @Insert
    suspend fun insertAll(users: List<entity>)

    @Delete
    suspend fun delete(user: entity)
}