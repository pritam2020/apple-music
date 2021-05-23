package com.example.apple

interface DatabaseHelper {
    suspend fun getUsers(): List<entity>

    suspend fun insertAll(users: List<entity>)
}