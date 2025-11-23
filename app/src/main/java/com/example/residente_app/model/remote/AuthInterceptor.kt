package com.example.residente_app.model.remote

import com.example.residente_app.data.store.TokenStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStore: TokenStore): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking { tokenStore.accessToken.first() }

        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else chain.request()

        val response = chain.proceed(request)

        return response
    }
}
