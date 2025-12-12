package com.example.residente_app.model.remote

import android.content.Context
import com.example.residente_app.model.remote.interfaces.UserAppAPInterface
import com.example.residente_app.model.remote.RetrofitInstance
import com.example.residente_app.model.remote.interfaces.ResidenceAPInterface
import retrofit2.create

object RetrofitProviders{
    fun provideUserAppAPI(context: Context): UserAppAPInterface{
        return RetrofitInstance.getRetrofit(context)
            .create(UserAppAPInterface::class.java)
    }

    fun provideResidenceAPI(context: Context): ResidenceAPInterface{
        return RetrofitInstance.getRetrofit(context)
            .create(ResidenceAPInterface::class.java)
    }
}