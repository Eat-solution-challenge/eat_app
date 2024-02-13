package com.example.eat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.eat.RetrofitAPI
import com.example.eat.main.MainActivity
import com.example.eat.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLogin.setOnClickListener{
            val userData=LoginRequest(
                binding.editTextId.text.toString()?:"",
                binding.editTextPassword.text.toString()?:""
            )
            RetrofitLogin(userData).work()

            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)   //mainActivity로 이동
        }
        binding.buttonRegister.setOnClickListener{
            val intent=Intent(this,Register1Activity::class.java)
            startActivity(intent)   //Register1Activity로 이동
        }
    }

}
class RetrofitLogin(private val userInfo: LoginRequest) {
    fun work() {
        val service = RetrofitAPI.getLoginServiceInstance()
        service.userLogin(userInfo)
            .enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("로그인 성공", "$result")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("로그인 실패 $t", t.message.toString())
                }
            })
    }
}