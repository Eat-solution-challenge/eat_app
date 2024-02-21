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
                        // UI에 바인딩할 데이터를 가져옴
                        val dataForBinding = listOf(
                            FiveTuple(binding.titleTime, binding.recordedMenu, binding.recordedConsumption,binding.recordedSatiety,binding.recordedCalorie),
                            FiveTuple(binding.titleTime2, binding.recordedMenu2, binding.recordedConsumption2,binding.recordedSatiety2,binding.recordedCalorie2),
                            FiveTuple(binding.titleTime3, binding.recordedMenu3, binding.recordedConsumption3,binding.recordedSatiety3,binding.recordedCalorie3)
                        )
                        // 데이터를 가져와 UI에 바인딩
                        for ((title, menu, consumption, satiety, calorie) in dataForBinding) {
                            if (iterator.hasNext()) {
                                val response = iterator.next()
                                binding.recordDate.text=response.createdTime
                                title.text = response.timeslot
                                menu.text = response.menu
                                consumption.text = "${response.intake} ${response.unit}"
                                satiety.text=getSatiety(response.level)
                                calorie.text=response.calorie.toString()
                            } else {
                                // 응답 데이터가 부족할 경우 해당 UI를 숨김
                                title.visibility = View.GONE
                                menu.visibility = View.GONE
                                consumption.visibility = View.GONE
                                satiety.visibility = View.GONE
                                calorie.visibility = View.GONE
                                binding.titleCalorie.visibility = View.GONE
                                binding.titleCalorie2.visibility = View.GONE
                                binding.titleCalorie3.visibility = View.GONE
                                binding.titleConsumption.visibility = View.GONE
                                binding.titleConsumption2.visibility = View.GONE
                                binding.titleConsumption3.visibility = View.GONE
                                binding.titleMenu.visibility = View.GONE
                                binding.titleMenu2.visibility = View.GONE
                                binding.titleMenu3.visibility = View.GONE
                                binding.titleSatiety.visibility = View.GONE
                                binding.titleSatiety2.visibility = View.GONE
                                binding.titleSatiety3.visibility = View.GONE
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