package com.example.eat.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface JoinService{
    @Headers("Content-Type: application/json")
    @POST("/user/join")
    fun addUser(@Body data: JoinRequest): Call<JoinResponse>
}