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
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)   //activityLogin으로 다시 이동
        }
    }

}