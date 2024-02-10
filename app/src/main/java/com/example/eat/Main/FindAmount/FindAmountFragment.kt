package com.example.eat.Main.FindAmount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.Main.Record.RecordFragment
import com.example.eat.R
import com.example.eat.databinding.FragmentCheckRecordBinding
import com.example.eat.databinding.FragmentFindAmountBinding

class FindAmountFragment: Fragment() {
    private val findAmountFragment: FindAmountFragment by lazy { FindAmountFragment() }
    private var _binding: FragmentFindAmountBinding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null  // 전역 변수로 args 선언

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindAmountBinding.inflate(inflater, container, false)

        args = arguments    //RecordFragment에서 저장된 bundle객체
        if (args != null) {
        }
        binding.korButton.setOnClickListener {

        }
        fun replaceFragment() {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            //main_container의 CheckRecordFragment로 transaction 한다.
            transaction.addToBackStack(null)    //back stack에 RecordFrament push
            //transaction.replace(R.id.main_container, korRecordActivity)
            transaction.commit()
        }
        return binding.root
    }
}