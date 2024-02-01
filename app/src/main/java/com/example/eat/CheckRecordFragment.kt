package com.example.eat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.eat.databinding.FragmentCheckRecordBinding
import com.example.eat.databinding.FragmentRecordBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CheckRecordFragment: Fragment() {
    private var _binding: FragmentCheckRecordBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckRecordBinding.inflate(inflater, container, false)

        binding.saveRecord.setOnClickListener {
            saveData()  //저장
            finish()
        }
        return binding.root
    }
    private fun saveData(){ }
    private fun finish(){ }
}