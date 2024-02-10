package com.example.eat.main.Record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.databinding.FragmentCheckRecordBinding

class CheckRecordFragment: Fragment() {
    private val recordFragment: RecordFragment by lazy { RecordFragment() }
    private var _binding: FragmentCheckRecordBinding?=null
    private val binding get()=_binding!!
    private var args: Bundle? = null  // 전역 변수로 args 선언

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckRecordBinding.inflate(inflater, container, false)

        args = arguments    //RecordFragment에서 저장된 bundle객체
        if (args != null) {
            val time = args!!.getString("time", "")
            val mainCategory = args!!.getString("mainCategory", "")
            val subCategory = args!!.getString("subCategory", "")
            val menuName = args!!.getString("menuName", "")
            val consumption = args!!.getString("consumption", "")
            val satiety = args!!.getString("satiety", "")
            val memo = args!!.getString("memo", "")
            val calories = args!!.getString("calories", "")
            val carbohydrate = args!!.getString("carbohydrate", "")
            val fat = args!!.getString("fat", "")
            val protein = args!!.getString("protein", "")
            val sugar = args!!.getString("sugar", "")

            binding.selectedTime.text=time
            binding.recordedCategory.text=mainCategory+"/   "+subCategory
            binding.recordedConsumption.text=consumption
            binding.recordedSatiety.text=satiety
            binding.recordedMenu.text=menuName
            binding.recordedMemo.text=memo
            binding.recordCalories.setText(calories)
            binding.recordCarbohydrate.setText(carbohydrate)
            binding.recordFat.setText(fat)
            binding.recordProtein.setText(protein)
            binding.recordSugar.setText(sugar)

        }
        binding.saveRecord.setOnClickListener {
            saveData()  //저장
            toRecordFragment()    //종료 후 초기화된 RecordFrame으로
        }

        binding.editRecord.setOnClickListener {
            toRecordFragment()   //recordFragment로 돌아감
        }
        return binding.root
    }
    fun toRecordFragment() {
        requireActivity().supportFragmentManager.popBackStack() //checkRecordFragment 나가기
    }

    private fun saveData(){ //서버에 저장

    }
}