package com.example.residente_app.data.database

import com.example.residente_app.data.dao.UserDao

abstract class UserDatabase {
    abstract fun UserDao(): UserDao
}