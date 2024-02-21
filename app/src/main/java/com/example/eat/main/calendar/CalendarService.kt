package com.example.eat.main.calendar

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CalendarService {
    @GET("/calender")
    fun getCalendar(
        @Header("X-ACCESS-TOKEN") token: String,
        @Query("date") date:String
    ): Call<List<CalendarResponse>>
}
