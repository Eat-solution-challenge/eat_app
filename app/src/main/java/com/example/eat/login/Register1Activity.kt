package com.example.eat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eat.databinding.ActivityRegister1Binding

class Register1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegister1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener{
            if(binding.editTextMail.text.toString() == "") {
                Toast.makeText(this@Register1Activity, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("email","이메일 없음")
            }
            else if(binding.editTextNickname.text.toString() == "") {
                Toast.makeText(this@Register1Activity, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("nick","닉네임 없음")
            }
            else if(binding.editTextPassword.text.toString() != binding.editTextCheckPassword.text.toString()) {
                Toast.makeText(this@Register1Activity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                Log.d("pw","비밀번호 다름")
            }
            else {
                val intent = Intent(this, Register2Activity::class.java)
                intent.apply {  //기본 정보 다음 화면에 전달
                    this.putExtra("email", binding.editTextMail.text.toString())
                    this.putExtra("nickname", binding.editTextNickname.text.toString())
                    this.putExtra("password", binding.editTextPassword.text.toString())
                }
                startActivity(intent)   //activityRegister2로 이동
            }
        }
    }

}