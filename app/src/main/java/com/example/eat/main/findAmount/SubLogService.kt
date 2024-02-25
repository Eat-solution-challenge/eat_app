package com.example.eat.main.findAmount

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SubLogService {
    @GET("/logs")
    fun getLog(
        @Header("X-ACCESS-TOKEN") token: String,
        @Query("subCategoryId") subCategoryId:Int
    ): Call<List<ResponseSubLog>>
}