package com.example.eat.login

import com.google.gson.annotations.SerializedName

data class JoinRequest (    @SerializedName("email")
                            val email: String,
                            @SerializedName("nickname")
                            val nickname: String,
                            @SerializedName("password")
                            val password: String,
                            @SerializedName("height")
                            val height: Long,
                            @SerializedName("weight")
                            val weight: Long,
                            @SerializedName("gender")
                            val gender: String,
                            @SerializedName("age")
                            val age: Int)

data class JoinData(    //id랑 닉네임은 필요 없음?
    @SerializedName("email")
    val mail: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("height")
    val height: Long,
    @SerializedName("weight")
    val weight: Long,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("age")
    val age: Int
)