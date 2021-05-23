package com.example.apple

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<entity> = appDatabase.userDao().getAll()
    override suspend fun insertAll(users: List<entity>) = appDatabase.userDao().insertAll(users)

}