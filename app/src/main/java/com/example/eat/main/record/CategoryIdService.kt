package com.example.eat.main.record

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryIdService {
    @GET("/categories/{categoryId}")
    fun getRecord(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("categoryId") categoryId: Int
        ): Call<List<CategoryIDResponse>>
}