package com.example.tarea2.data.remote.services

import com.example.tarea2.model.LoginRequest
import com.example.tarea2.model.LoginResponse
import com.example.tarea2.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/profile")
    suspend fun getUser(): User

    @POST("logout")
    suspend fun logout()
}