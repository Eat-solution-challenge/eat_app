package com.example.eat.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService{
    @Headers("Content-Type: application/json")
    @POST("/user/login")
    fun userLogin(@Body data: LoginRequest): Call<LoginResponse>
}