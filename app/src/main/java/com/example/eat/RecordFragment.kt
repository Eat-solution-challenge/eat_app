package com.example.eat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.eat.databinding.FragmentRecordBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordFragment: Fragment() {
    private var _binding: FragmentRecordBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentRecordBinding.inflate(inflater,container,false)
        binding.spinnerMainCategory.adapter=ArrayAdapter.createFromResource(requireContext(),R.array.main_category,R.layout.custom_spinner_dropdown_item)
        binding.spinnerUnit.adapter=ArrayAdapter.createFromResource(requireContext(),R.array.unit,android.R.layout.simple_list_item_1)
        binding.textViewSubcategory.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.subcategory,android.R.layout.simple_dropdown_item_1line))//자동완성
        binding.textViewMenu.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.menu,android.R.layout.simple_dropdown_item_1line))//자동완성
        val currentDate=LocalDate.now() //currentDate에 기록일 저장
        val formatter=DateTimeFormatter.ofPattern("MM월 dd일")
        binding.recordDate.text= currentDate.format(formatter) //상단에 기기 날짜 표시

        binding.finishRecord.setOnClickListener {
            //기록 확인 fragment로
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null   //바인딩 클래스 인스턴스 정리
    }

    private fun getTime():String{
        val time=if(binding.radioButtonBreakfast.isChecked) "breakfast"
        else if (binding.radioButtonLunch.isChecked) "lunch"
        else if (binding.radioButtonDinner.isChecked) "dinner"
        else ""
        return time
    }
    private fun getMainCategory():String=binding.spinnerMainCategory.selectedItem.toString()
    private fun getSubcategory():String=binding.textViewSubcategory.toString()
    private fun getMenuName():String=binding.textViewMenu.toString()
    private fun getConsumption():String{
        val consumption=binding.recordConsumption.toString()
        val consumptionUnit=binding.spinnerUnit.selectedItem.toString()
        return "$consumption $consumptionUnit"
    }
    private fun getSatiety():Int{
        val satiety=if(binding.satietyButton1.isChecked) 0
        else if (binding.satietyButton2.isChecked) 1
        else if (binding.satietyButton3.isChecked) 2
        else ""
        return satiety as Int
    }
    private fun getMemo():String {  //글자수 넘으면 경고창or 더 못적게
        val memo=binding.addMemo.toString()
        if(!memo.isNullOrEmpty())
            return memo
        else return ""
    }
}