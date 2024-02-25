package com.example.eat.main.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.databinding.FragmentCheckRecordBinding
import com.example.eat.login.token
import com.example.eat.main.MainActivity
import retrofit2.Call
import retrofit2.Response


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
            val date = args!!.getString("date", "")
            val time = args!!.getString("time", "")
            val mainCategory = args!!.getString("mainCategory", "")
            val subCategory = args!!.getString("subCategory", "")
            val menuName = args!!.getString("menuName", "")
            val consumption = args!!.getString("consumption", "")
            val consumptionUnit = args!!.getString("consumptionUnit", "")
            val satiety = args!!.getString("satiety", "")
            val memo = args!!.getString("memo", "")
            val calories = args!!.getString("calories", "")
            val carbohydrate = args!!.getString("carbohydrate", "")
            val fat = args!!.getString("fat", "")
            val protein = args!!.getString("protein", "")
            val sugar = args!!.getString("sugar", "")

            binding.recordDate.text=date
            binding.selectedTime.text=time
            binding.recordedCategory.text=mainCategory+"/   "+subCategory
            binding.recordedConsumption.text=consumption+" "+consumptionUnit
            binding.recordedSatiety.text=satiety
            binding.recordedMenu.text=menuName
            binding.nutrient.text=menuName.toString()+"의 영양성분"
            binding.recordedMemo.text=memo
            binding.recordedCalories.setText(calories)
            binding.recordCarbohydrate.setText(carbohydrate)
            binding.recordFat.setText(fat)
            binding.recordProtein.setText(protein)
            binding.recordSugar.setText(sugar)

        }
        binding.saveRecord.setOnClickListener {
            saveData()  //저장
            // 백스택에 있는 항목 모두 종료
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, RecordFragment())
            transaction.commit()
        }

        binding.editRecord.setOnClickListener {
            toRecordFragment()   //recordFragment로 돌아감
        }

        return binding.root
    }

    private fun getCategoryID(): Int{
        return when (val mainCategory = args?.getString("mainCategory", "") ?: "") {
            "분식" -> 1
            "일식" -> 2
            "한식" -> 3
            "양식" -> 4
            "중식" -> 5
            "아시안" -> 6
            "간식" -> 7
            "디저트" -> 8
            else -> {
                // 기본값 또는 에러 처리
                Log.e("getCategoryID", "Unknown mainCategory: $mainCategory")
                // 기본값으로 0을 반환하거나, 에러 처리에 따라 다른 값을 반환할 수 있습니다.
                0
            }
        }
    }

    private fun getLevel(): Int {
        return when (val level = args?.getString("satiety", "") ?: "") {
            "소식" -> 0
            "적정" -> 1
            "과식" -> 2
            else -> {
                // 기본값 또는 에러 처리
                Log.e("getLevel", "Unknown level: $level")
                // 기본값으로 -1을 반환하거나, 에러 처리에 따라 다른 값을 반환할 수 있습니다.
                -1
            }
        }
    }

    fun toRecordFragment() {
        requireActivity().supportFragmentManager.popBackStack() //checkRecordFragment 나가기
    }

    private fun saveData() {    //서버에 저장
        val categoryID = getCategoryID()
        val recordRequest = RecordRequest(
            subCategory = args?.getString("subCategory", "") ?: "",
            menu = args?.getString("menuName", "") ?: "",
            intake = args?.getString("consumption", "")?.toDoubleOrNull() ?: 0.0,
            unit = args?.getString("consumptionUnit", "") ?: "",
            level = getLevel(),
            calorie = args?.getString("calories", "")?.toDoubleOrNull() ?: 0.0,
            fat = args?.getString("fat", "")?.toDoubleOrNull() ?: 0.0,
            protein = args?.getString("protein", "")?.toDoubleOrNull() ?: 0.0,
            carbs = args?.getString("carbohydrate", "")?.toDoubleOrNull() ?: 0.0,
            sugar = args?.getString("sugar", "")?.toDoubleOrNull() ?: 0.0,
            memo = args?.getString("memo", "") ?: "",
            timeslot=args?.getString("time", "") ?: ""
        )
        RetrofitRecord(categoryID, recordRequest).work()
    }


}

class RetrofitRecord(private val categoryId: Int, private val recordRequest: RecordRequest) {
    fun work() {
        val service = RetrofitAPI.getRecordServiceInstance()
        service.postRecord(
            token,
            categoryId, recordRequest)
            .enqueue(object : retrofit2.Callback<RecordResponse> {
                override fun onResponse(
                    call: Call<RecordResponse>,
                    response: Response<RecordResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Toast.makeText(MainActivity(),"저장이 완료되었습니다.",Toast.LENGTH_SHORT)
                        Log.d("저장 성공", "$result")
                    }
                    else
                        Toast.makeText(MainActivity(),"내용을 전부 입력해주세요.",Toast.LENGTH_SHORT)
                }

                override fun onFailure(call: Call<RecordResponse>, t: Throwable) {
                    Log.d("저장 실패 $t", t.message.toString())
                }
            })
    }
}