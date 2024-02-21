package com.example.eat.main.calendar

import com.google.gson.annotations.SerializedName

data class CalendarResponse (
    val id: Int,
    @SerializedName("createdTime") val createdTime: String, // JSON 키와 변수 이름이 다른 경우 SerializedName 사용
    val level: String,
    val menu: String,
    val intake: Double,
    val unit: String,
    val calorie: Int,
    val timeslot:String
)