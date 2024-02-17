package com.example.eat.main.my

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface MyService {
    @GET("/user/mypage")

    fun getMy(
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<MyResponse>
}