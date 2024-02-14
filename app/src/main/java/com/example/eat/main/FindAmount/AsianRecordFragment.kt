package com.example.eat.main.findAmount


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.databinding.FragmentAsianRecordBinding

class AsianRecordFragment: Fragment() {
    private val findAmountFragment: FindAmountFragment by lazy { FindAmountFragment() }
    private var _binding: FragmentAsianRecordBinding?=null
    private val binding get()=_binding!!
    private var args: Bundle? = null  // 전역 변수로 args 선언

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAsianRecordBinding.inflate(inflater, container, false)

        return binding.root
    }

}