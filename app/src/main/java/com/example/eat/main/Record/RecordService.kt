package com.example.eat.main.record

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordService {
    @POST("/categories/{categoryId}")
    fun postRecord(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("categoryId") categoryId: Int,
        @Body data: RecordRequest): Call<RecordResponse>
}