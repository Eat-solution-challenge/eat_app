package com.example.eat.main.record

data class CalorieResponse (
    val id: Int,
    val menu: String,
    val intake: Double,
    val unit: String,
    val level:String,
    val calorie: Int,
    val fat: Double,
    val protein: Double,
    val carbs: Double,
    val sugar: Double,
    val memo: String
)