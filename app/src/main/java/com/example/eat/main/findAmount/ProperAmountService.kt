package com.example.eat.main.findAmount

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProperAmountService {
    @GET("/proper")
    fun getProperAmount(
        @Header("X-ACCESS-TOKEN") token : String,
        @Query("subCategoryName") subCategoryName: String,
        @Query("unit") unit: String): Call<ProperAmountResponse>
}