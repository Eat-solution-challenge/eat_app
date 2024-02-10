package com.example.eat.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eat.main.FindAmount.FindAmountFragment
import com.example.eat.main.My.MyFragment
import com.example.eat.main.Record.RecordFragment
import com.example.eat.main.ViewRecord.ViewRecordFragment
import com.example.eat.R
import com.example.eat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_find_amount
        }
    }

    private fun setBottomNavigationView(){
        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when (item.itemId) {
                R.id.fragment_find_amount -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, FindAmountFragment()).commit()
                    true
                }

                R.id.fragment_record -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, RecordFragment()).commit()
                    true
                }

                R.id.fragment_view_record -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ViewRecordFragment()).commit()
                    true
                }

                R.id.fragment_my -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, MyFragment()).commit()
                    true
                }

                else -> false
            }
        }
    }
}