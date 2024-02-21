package com.example.eat.main.findAmount

import java.time.LocalDateTime

data class ResponseWaste (
    val id : Long,
    val createdTime : LocalDateTime,
    val amount : Double
)