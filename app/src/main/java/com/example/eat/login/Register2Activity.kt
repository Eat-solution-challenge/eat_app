package com.example.eat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.eat.databinding.ActivityRegister2Binding
import retrofit2.Call
import retrofit2.Response

class Register2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegister2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener{
            val userData=JoinRequest(JoinData(
                intent.getStringExtra("mail")?:"",
                intent.getStringExtra("nickname")?:"",
                intent.getStringExtra("password")?:"",
                binding.editTextHeight.text.toString().toLong()?:0L,
                binding.editTextWeight.text.toString().toLong()?:0L,
                getGender(),
                binding.editTextAge.text.toString().toInt()?:0
                ))
            val retrofitWork = RetrofitWork(userData)
            retrofitWork.work()

            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)   //activityLogin으로 다시 이동
        }
    }
    private fun getGender():String{
        val gender=if(binding.radioButtonWoman.isChecked) "여성"
        else if (binding.radioButtonMan.isChecked) "남성"
        else ""
        return gender
    }

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
                    Log.d("회원가입 실패 $t", t.message.toString())
                }
            })
    }
}