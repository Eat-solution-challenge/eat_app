package com.example.eat.main.record

data class RecordRequest (
    val subCategory : String,
    val menu : String,
    val intake : Double,
    val unit : String,
    val level : Int,
    val calorie : Double,
    val fat : Double,
    val protein : Double,
    val carbs : Double,
    val sugar : Double,
    val memo : String,
    val timeslot:String
)