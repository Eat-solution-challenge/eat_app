package com.example.eat.main.calendar

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
import com.example.eat.databinding.FragmentCalendar2Binding
import com.example.eat.login.token
import com.example.eat.main.findAmount.ProperAmountResponse
import com.example.eat.main.record.RecordFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        var date: String=""
        if (args != null) {
            date = args!!.getString("date", "")
        }
        getCalendar(date)
    // 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
        binding.buttonBack.setOnClickListener{
            // 캘린더로 돌아가기
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun getCalendar(date: String) {
        // Retrofit을 사용하여 API 호출하고 응답 처리
        RetrofitAPI.getCalendarServiceInstance().getCalendar(
            token,
            date
        ).enqueue(object : Callback<List<CalendarResponse>> {
            override fun onResponse(
                call: Call<List<CalendarResponse>>,
                response: Response<List<CalendarResponse>>
            ) {
                if (response.isSuccessful) {
                    val calendarResponses = response.body()
                    if (calendarResponses != null && calendarResponses.isNotEmpty()) {
                        val iterator = calendarResponses.iterator()
                        var dataIndex = 0

                        val titleList = listOf(binding.titleTime, binding.titleTime2, binding.titleTime3)
                        val menuList = listOf(binding.recordedMenu, binding.recordedMenu2, binding.recordedMenu3)
                        val consumptionList = listOf(binding.recordedConsumption, binding.recordedConsumption2, binding.recordedConsumption3)
                        val satietyList = listOf(binding.recordedSatiety, binding.recordedSatiety2, binding.recordedSatiety3)
                        val calorieList = listOf(binding.recordedCalorie, binding.recordedCalorie2, binding.recordedCalorie3)
                        val titleCalorie = listOf(binding.titleCalorie, binding.titleCalorie2, binding.titleCalorie3)
                        val titleConsumption = listOf(binding.titleConsumption, binding.titleConsumption2, binding.titleConsumption3)
                        val titleMenu = listOf(binding.titleMenu, binding.titleMenu2, binding.titleMenu3)
                        val titleSatiety = listOf(binding.titleSatiety, binding.titleSatiety2, binding.titleSatiety3)

                        for ((index, title) in titleList.withIndex()) {
                            if (iterator.hasNext()) {
                                val response = iterator.next()
                                binding.recordDate.text = response.createdTime
                                title.text = response.timeslot
                                menuList[index].text = response.menu
                                consumptionList[index].text = "${response.intake} ${response.unit}"
                                satietyList[index].text = getSatiety(response.level)
                                calorieList[index].text = response.calorie.toString()
                                dataIndex++
                            } else {
                                // 데이터가 부족한 경우 해당 UI를 숨김 또는 내용을 지움
                                title.visibility = View.GONE
                                menuList[index].visibility = View.GONE
                                consumptionList[index].visibility = View.GONE
                                satietyList[index].visibility = View.GONE
                                calorieList[index].visibility = View.GONE
                                titleCalorie[index].visibility = View.GONE
                                titleConsumption[index].visibility = View.GONE
                                titleMenu[index].visibility = View.GONE
                                titleSatiety[index].visibility = View.GONE
                            }
                        }
                        Log.d("데이터 로드 성공", "데이터 로드 성공")
                    } else {
                        Log.e("데이터 로드 실패", "응답 데이터가 null이거나 비어 있습니다.")
                    }
                } else {
                    Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<List<CalendarResponse>>, t: Throwable) {
                Log.e("실패 $t", t.message.toString())
            }
        })
    }


    private fun getSatiety(level:String):String{
        return when(level){
            "LEVEL_OVEREAT"->"과식"
            "LEVEL_PROPER"->"적정"
            "LEVEL_LIGHT"->"소식"
            else ->""
        }
    }

}
data class FiveTuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)