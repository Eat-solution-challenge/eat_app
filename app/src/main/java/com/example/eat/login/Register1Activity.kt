package com.example.eat.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eat.databinding.ActivityRegister1Binding

class Register1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegister1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener{
            val intent= Intent(this, Register2Activity::class.java)
            intent.apply {  //기본 정보 다음 화면에 전달
                this.putExtra("mail", binding.editTextMail.text.toString())
                this.putExtra("nickname", binding.editTextNickname.text.toString())
                this.putExtra("password", binding.editTextPassword.text.toString())
            }

            startActivity(intent)   //activityRegister2로 이동
        }
    }

}