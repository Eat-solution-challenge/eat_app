package com.example.eat.login

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    val grantType : String,
    @SerializedName("jwtAccessToken")
    val accessToken : String,
    @SerializedName("jwtRefreshToken")
    val refreshToken : String
)