package com.example.eat.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eat.databinding.ActivityRegister2Binding

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