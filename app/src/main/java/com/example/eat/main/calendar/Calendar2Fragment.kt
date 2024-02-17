package com.example.eat.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eat.R
import com.example.eat.databinding.FragmentCalendar2Binding
import com.example.eat.main.record.RecordFragment


class Calendar2Fragment : Fragment() {
    private val calendar2Fragment: Calendar2Fragment by lazy { Calendar2Fragment() }
    private var _binding: FragmentCalendar2Binding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null  // 전역 변수로 args 선언

    var fname: String = ""
    var str: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendar2Binding.inflate(inflater, container, false)
        args = arguments
        var year: Int = 0
        var month: Int = 0
        var day: Int = 0
        if (args != null) {
            year = args!!.getInt("year", 0)
            month = args!!.getInt("month", 0)
            day = args!!.getInt("day", 0)
        }
        binding.diaryTextView.text = String.format("%d년 %d월 %d일", year,month+1,day)
    // 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.

        binding.buttonBack.setOnClickListener{
            // 캘린더로 돌아가기
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
}
