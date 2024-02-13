package com.example.eat.login

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    private const val BASE_URL = "http://43.202.226.78:8080/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val joinService: JoinService by lazy {
        retrofit.create(JoinService::class.java)
    }

    fun getJoinServiceInstance(): JoinService {
        return joinService
    }

    private val loginService : LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    fun getLoginServiceInstance(): LoginService {
        return loginService
    }
}
