package com.example.eat.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eat.main.MainActivity
import com.example.eat.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)   //mainActivity로 이동
        }
        binding.buttonRegister.setOnClickListener{
            val intent=Intent(this,Register1Activity::class.java)
            startActivity(intent)   //Register1Activity로 이동
        }
    }

}