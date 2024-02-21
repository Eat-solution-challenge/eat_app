package com.example.eat.main.record
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CalorieService {
    @GET("/logs/search")
    fun getCalorieService(
        @Header("X-ACCESS-TOKEN") token: String,
        @Query("menu") menu: String
    ): retrofit2.Call<List<CalorieResponse>>
}