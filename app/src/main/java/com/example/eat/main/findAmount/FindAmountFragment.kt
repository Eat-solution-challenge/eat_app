package com.example.eat.main.findAmount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.databinding.FragmentFindAmountBinding


class FindAmountFragment: Fragment() {
    private val korRecordFragment: KorRecordFragment by lazy { KorRecordFragment() }
    private var _binding: FragmentFindAmountBinding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null  // 전역 변수로 args 선언

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindAmountBinding.inflate(inflater, container, false)

        binding.korButton.setOnClickListener {
            toKorFragment()
        }

        return binding.root
    }
    private fun toKorFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        //main_container의 CheckRecordFragment로 transaction 한다.
        transaction.addToBackStack(null)    //back stack에 KorRecordFragment push
        transaction.replace(R.id.main_container, korRecordFragment)
        transaction.commit()
    }
}