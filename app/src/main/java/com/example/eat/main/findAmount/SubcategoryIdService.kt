package com.example.eat.main.findAmount
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
interface SubcategoryIdService {
    @GET("/categories/{categoryId}")
    fun getSubcategoryID(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("categoryId") categoryId: Int
    ): Call<List<SubcategoryIDResponse>>
}