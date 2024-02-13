package com.example.eat.login

import com.google.gson.annotations.SerializedName

data class JoinResponse(
    @SerializedName("status")
    val status: String
)