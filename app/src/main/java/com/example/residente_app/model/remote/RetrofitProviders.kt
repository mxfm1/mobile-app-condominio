package com.example.residente_app.model.remote

import android.content.Context
import com.example.residente_app.model.remote.interfaces.UserAppAPInterface
import com.example.residente_app.model.remote.RetrofitInstance
import retrofit2.create

object RetrofitProviders{
    fun provideUserAppAPI(context: Context): UserAppAPInterface{
        return RetrofitInstance.getRetrofit(context)
            .create(UserAppAPInterface::class.java)
    }
}