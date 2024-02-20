package com.example.eat.main.findAmount

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProperAmountService {
    @GET("/proper")
    fun getProperAmount(@Query("subCategoryName") subCategoryName: String): Call<ProperAmountResponse>
}