package com.example.residente_app.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.residente_app.model.local.AppDatabase
import com.example.residente_app.model.repository.AuthRepository

class AuthViewModelFactory(private val app: Application): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        val db = AppDatabase.get(app)
        val repo = AuthRepository(db.userDao())
        return LoginViewModel(repo) as T
    }
}