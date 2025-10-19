package com.example.residente_app.data.dao

import androidx.lifecycle.LiveData
import com.example.residente_app.data.entity.User

interface UserDao {
    suspend fun addUser(user: User)

    fun readAllData(): LiveData<List<User>>
}