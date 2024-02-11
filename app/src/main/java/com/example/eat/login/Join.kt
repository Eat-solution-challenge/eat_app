package com.example.eat.login

import android.util.Log
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


data class JoinResponse(
    @SerializedName("status")
    val status: String
)
data class JoinRequest (val data: JoinData )
data class JoinData(    //id랑 닉네임은 필요 없음?
    @SerializedName("mail")
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
    val age: Int
)
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
}
interface JoinService{
    @Headers("Content-Type: application/json")
    @POST("/user/join")
    fun addUser(@Body data: JoinRequest): Call<JoinResponse>
}
class RetrofitWork(private val userInfo: JoinRequest) {
    fun work() {
        val service = RetrofitAPI.getJoinServiceInstance()
        service.addUser(userInfo)
            .enqueue(object : retrofit2.Callback<JoinResponse> {
                override fun onResponse(
                    call: Call<JoinResponse>,
                    response: Response<JoinResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("회원가입 성공", "$result")
                    }
                }

                override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                    Log.d("회원가입 실패", t.message.toString())
                }
            })
    }
}