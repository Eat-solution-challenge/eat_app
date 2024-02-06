package com.example.eat

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

            binding.selectedTime.text=time
            binding.recordedCategory.text=mainCategory+"/   "+subCategory
            binding.recordedConsumption.text=consumption
            binding.recordedSatiety.text=satiety
            binding.recordedMenu.text=menuName
            binding.recordedMemo.text=memo
        }
        binding.saveRecord.setOnClickListener {
            saveData()  //저장
            replaceFragment()    //종료 후 초기화된 RecordFrame으로
        }

        binding.editRecord.setOnClickListener {
            editData()  //RecordFragment에 bundle 전달
            replaceFragment()   //recordFragment로 돌아감
        }
        return binding.root
    }
    fun replaceFragment() {
        requireActivity().supportFragmentManager.popBackStack() //checkRecordFragment 나가기
    }
    private fun editData():Bundle{
        val editedBundle = Bundle()
        args?.let {
            // args가 null이 아닌 경우에만 데이터를 복사
            editedBundle.putString("time", it.getString("time", ""))
            editedBundle.putString("mainCategory", it.getString("mainCategory", ""))
            editedBundle.putString("subCategory", it.getString("subCategory", ""))
            editedBundle.putString("menuName", it.getString("menuName", ""))
            editedBundle.putString("consumption", it.getString("consumption", ""))
            editedBundle.putString("satiety", it.getString("satiety", ""))
            editedBundle.putString("memo", it.getString("memo", ""))
        }

        // recordFragment의 arguments에 수정된 Bundle 할당
        recordFragment.arguments = editedBundle

        return editedBundle
    }
    private fun saveData(){

    }
}