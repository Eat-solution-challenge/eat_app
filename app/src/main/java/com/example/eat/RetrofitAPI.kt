package com.example.eat

import com.example.eat.login.JoinService
import com.example.eat.login.LoginService
import com.example.eat.main.calendar.CalendarService
import com.example.eat.main.record.CategoryIdService
import com.example.eat.main.record.RecordService
import com.example.eat.main.my.MyService
import com.example.eat.main.findAmount.ProperAmountService
import com.example.eat.main.findAmount.WasteService
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

    private val recordService : RecordService by lazy {
        retrofit.create(RecordService::class.java)
    }

    fun getRecordServiceInstance(): RecordService {
        return recordService
    }

    private val categoryIdService : CategoryIdService by lazy {
        retrofit.create(categoryIdService::class.java)
    }

    fun getCategoryIdServiceInstance(): CategoryIdService {
        return categoryIdService
    }

    fun getMyServiceInstance(): MyService {
        return retrofit.create(MyService::class.java)
    }

    fun getProperAmountInstance(): ProperAmountService {
        return retrofit.create(ProperAmountService::class.java)
    }

    fun getWasteServiceInstance(): WasteService {
        return retrofit.create(WasteService::class.java)
    }

    fun getCalendarServiceInstance(): CalendarService {
        return retrofit.create(CalendarService::class.java)
    }
}
