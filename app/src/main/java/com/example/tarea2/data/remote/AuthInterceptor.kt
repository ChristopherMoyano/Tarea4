package com.example.tarea2.data.remote

import android.content.Context
import com.example.tarea2.data.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            TokenManager.getToken(context)
        }

        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization","Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}