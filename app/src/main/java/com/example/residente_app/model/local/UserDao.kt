package com.example.residente_app.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.residente_app.model.local.User
@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun obtenerUsuarios(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun login(username:String,password:String):User?

    @Query("SELECT COUNT(*) FROM user WHERE role = 'admin'")
    suspend fun hasAdmin():Int

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun findUserByUsername(username:String): User?
}