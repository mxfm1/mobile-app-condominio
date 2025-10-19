package com.example.residente_app.data.repository

import androidx.lifecycle.LiveData
import com.example.residente_app.data.dao.UserDao
import com.example.residente_app.data.entity.User

class UserRepository(private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}