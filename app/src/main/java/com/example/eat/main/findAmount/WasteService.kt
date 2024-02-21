package com.example.eat.main.findAmount

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface WasteService {
    @Headers("Content-Type: application/json")
    @POST("/trashlog")
    fun postWaste(@Header("X-ACCESS-TOKEN") token : String, @Body data: RequestWaste ): Call<ResponseWaste>
}
