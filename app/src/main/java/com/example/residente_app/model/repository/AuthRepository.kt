package com.example.residente_app.model.repository

import com.example.residente_app.model.local.User
import com.example.residente_app.model.local.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepository(private val dao: UserDao) {
    suspend fun getUSers(): Flow<List<User>> = dao.obtenerUsuarios()

    suspend fun login(username:String, password:String): User?{
        return withContext(Dispatchers.IO){
            dao.login(username,password)
        }
    }

    suspend fun insertAdminUser(user: User){
        return withContext(context = Dispatchers.IO){
            dao.insertUser(user)
        }
    }
}