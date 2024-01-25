package com.example.eat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eat.databinding.ActivityMainBinding
private lateinit var binding:ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_find_amount
        }
    }

    fun setBottomNavigationView(){
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