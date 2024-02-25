package com.example.eat.main.record

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.databinding.FragmentRecordBinding
import com.example.eat.login.JoinData
import com.example.eat.login.JoinRequest
import com.example.eat.login.RetrofitWork
import com.example.eat.login.token
import com.example.eat.main.calendar.CalendarResponse
import com.example.eat.main.calendar.FiveTuple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecordFragment: Fragment() {
    private val checkRecordFragment: CheckRecordFragment by lazy { CheckRecordFragment() }
    private var _binding: FragmentRecordBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentRecordBinding.inflate(inflater,container,false)
        binding.spinnerMainCategory.adapter=ArrayAdapter.createFromResource(requireContext(),
            R.array.main_category,
            R.layout.custom_spinner_dropdown_item
        )
        binding.spinnerUnit.adapter=ArrayAdapter.createFromResource(requireContext(),
            R.array.unit,android.R.layout.simple_list_item_1)

        // RetrofitCategoryID를 생성하고 서버에서 값을 가져오도록 호출합니다.
        //val retrofitCategoryID = RetrofitCategoryID(binding.spinnerMainCategory.selectedItemPosition, resources.getStringArray(R.array.subcategory), binding)
        //retrofitCategoryID.work()

        binding.textViewSubcategory.setAdapter(ArrayAdapter.createFromResource(requireContext(),
            R.array.subcategory,android.R.layout.simple_dropdown_item_1line))//자동완성
        binding.textViewMenu.setAdapter(ArrayAdapter.createFromResource(requireContext(),
            R.array.menu,android.R.layout.simple_dropdown_item_1line))//자동완성

        val currentDate=LocalDate.now() //currentDate에 기록일 저장
        val formatter=DateTimeFormatter.ofPattern("MM월 dd일")
        binding.recordDate.text= currentDate.format(formatter) //상단에 기기 날짜 표시

        binding.buttonSearch.setOnClickListener {
            //영양성분 검색
            val menu=binding.textViewMenu.text.toString()  //메뉴명 불러오기
            getCalories(menu)   //서버에서 영양성분 불러오기
        }
        binding.finishRecord.setOnClickListener {
            if(binding.recordCarbohydrate.text.isNotBlank()
                &&binding.recordProtein.text.isNotBlank()
                &&binding.recordCarbohydrate.text.isNotBlank()
                &&binding.recordConsumption.text.isNotBlank()
                &&binding.recordFat.text.isNotBlank()
                &&binding.recordSugar.text.isNotBlank()
                &&binding.recordedCalories.text.isNotBlank()
                &&(binding.radioGroupTime.checkedRadioButtonId!=-1)
                &&(binding.radioGroupSatiety.checkedRadioButtonId!=-1))
            toCheckFragment()   //기록 끝내기
            else
                Toast.makeText(context,"내용을 전부 기입해주세요.",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun toCheckFragment() {
        // 미리 생성한 CheckRecordFragment의 arguments를 설정
        checkRecordFragment.arguments = createArguments()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        //main_container의 CheckRecordFragment로 transaction 한다.
        transaction.addToBackStack(null)    //back stack에 RecordFrament push
        transaction.replace(R.id.main_container, checkRecordFragment)
        transaction.commit()
    }
    private fun createArguments(): Bundle {
        // 새로운 Bundle을 생성, CheckRecordFragment로 전달할 데이터 저장
        val args = Bundle()
        args.putString("time", getTime())
        args.putString("mainCategory", getMainCategory())
        args.putString("subCategory", getSubcategory())
        args.putString("menuName", getMenuName())
        args.putString("consumption", getConsumption())
        args.putString("consumptionUnit", getConsumptionUnit())
        args.putString("satiety", getSatiety())
        args.putString("memo", getMemo())
        args.putString("calories",binding.recordedCalories.text.toString())
        args.putString("carbohydrate",binding.recordCarbohydrate.text.toString())
        args.putString("fat",binding.recordFat.text.toString())
        args.putString("protein",binding.recordProtein.text.toString())
        args.putString("sugar",binding.recordSugar.text.toString())
        args.putString("date",binding.recordDate.text.toString())
        return args
    }
    override fun onSaveInstanceState(outState: Bundle) {   //recordFragment가 백스택에 배치될 때
        super.onSaveInstanceState(outState)
        outState.putInt("selectedTime", binding.radioGroupTime.checkedRadioButtonId)
        outState.putInt("mainCategory",binding.spinnerMainCategory.selectedItemPosition)
        outState.putString("subCategory", getSubcategory())
        outState.putString("consumption",binding.recordConsumption.text.toString())
        outState.putInt("unit",binding.spinnerUnit.selectedItemPosition)
        outState.putInt("selectedSatiety", binding.radioGroupSatiety.checkedRadioButtonId)
        outState.putString("memo", getMemo())
        Log.d(TAG,"!!!!OnSaveInstanceCalled!!!")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) { //recordFragment 복원
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val selectedTimeId = savedInstanceState.getInt("selectedTime")
            val mainCategoryPosition = savedInstanceState.getInt("mainCategory")
            val memo = savedInstanceState.getString("memo")

            // 선택한 라디오 버튼 상태 복원
            if (selectedTimeId != -1) {
                binding.radioGroupTime.check(selectedTimeId)
            }
            // 스피너 상태 복원
            binding.spinnerMainCategory.setSelection(mainCategoryPosition)
            // 나머지 상태 복원

            // 메모 복원
            binding.addMemo.setText(memo ?: "")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null   //바인딩 클래스 인스턴스 정리
    }
    private var savedMemo: String = ""

    private fun getTime():String{
        val time=if(binding.radioButtonBreakfast.isChecked) "아침"
        else if (binding.radioButtonLunch.isChecked) "점심"
        else if (binding.radioButtonDinner.isChecked) "저녁"
        else ""
        return time
    }
    private fun getMainCategory():String=binding.spinnerMainCategory.selectedItem.toString()
    private fun getSubcategory():String=binding.textViewSubcategory.text.toString()
    private fun getMenuName():String=binding.textViewMenu.text.toString()
    private fun getConsumption():String= binding.recordConsumption.text.toString()
    private fun getConsumptionUnit():String =binding.spinnerUnit.selectedItem.toString()

    private fun getSatiety(): String {
        return if (binding.satietyButton1.isChecked) "소식"
        else if (binding.satietyButton2.isChecked) "적정"
        else if (binding.satietyButton3.isChecked) "과식"
        else ""
    }
    private fun getMemo():String {  //글자수 넘으면 경고창 or 더 못적게
        val memo=binding.addMemo.text.toString()
        return if(!memo.isNullOrEmpty())
            memo
        else ""
    }
    private fun getCalories(menu: String) {
        // Retrofit을 사용하여 API 호출하고 응답 처리
        RetrofitAPI.getCalorieServiceInstance().getCalorieService(
            token,
            menu
        ).enqueue(object : Callback<List<CalorieResponse>> {
            override fun onResponse(
                call: Call<List<CalorieResponse>>,
                response: Response<List<CalorieResponse>>
            ) {
                if (response.isSuccessful) {
                    val calorieResponses = response.body()
                    if (calorieResponses != null && calorieResponses.isNotEmpty()) {

                        binding.recordedCalories.setText(calorieResponses[0].calorie.toString())
                        binding.recordSugar.setText(calorieResponses[0].sugar.toString())
                        binding.recordProtein.setText(calorieResponses[0].protein.toString())
                        binding.recordFat.setText(calorieResponses[0].fat.toString())
                        binding.recordCarbohydrate.setText(calorieResponses[0].carbs.toString())

                    } else {
                        Toast.makeText(requireContext(),"저장된 영양성분 값이 없습니다.",Toast.LENGTH_SHORT)
                    }
                } else {
                    Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                }
            }
            override fun onFailure(call: Call<List<CalorieResponse>>, t: Throwable) {
                Log.e("실패", t.message.toString())
            }
        })
    }}
