package com.example.eat.main.calendar

import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.databinding.FragmentCalendarBinding
import com.example.eat.login.token
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CalendarFragment : Fragment() {
    private val calendar2Fragment: Calendar2Fragment by lazy { Calendar2Fragment() }
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    var fname: String = ""
    var str: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding.calendarView.setSelectedDate((CalendarDay.today()))
        binding.calendarView.addDecorators(SundayDecorator(), SaturdayDecorator())
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date.date)
            val args = Bundle().apply {
                putString("date", formattedDate)
            }
            toCalendar2Fragment(args,formattedDate)
        }


        return binding.root
    }
    private class SundayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val calendar = day.calendar
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            return dayOfWeek == Calendar.SUNDAY
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object : ForegroundColorSpan(Color.RED) {})
        }
    }

    /* 토요일 날짜의 색상을 설정하는 클래스 */
    private class SaturdayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val calendar = day.calendar
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            return dayOfWeek == Calendar.SATURDAY
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(object : ForegroundColorSpan(Color.BLUE) {})
        }
    }


    private fun toCalendar2Fragment(args: Bundle,date:String) {
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
                        calendar2Fragment.arguments = args
                        // 미리 생성한 CheckRecordFragment의 arguments를 설정
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        //main_container의 CheckRecordFragment로 transaction 한다.
                        transaction.addToBackStack(null)    //back stack에 RecordFrament push
                        transaction.replace(R.id.main_container, calendar2Fragment)
                        transaction.commit()
                        Log.d("데이터 로드 성공", "데이터 로드 성공")
                    } else {
                        Toast.makeText(requireContext(),"기록이 없습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("데이터 로드 실패", "응답 데이터가 null이거나 비어 있습니다.")
                    }
                } else {
                    Toast.makeText(context,"기록이 없습니다.",Toast.LENGTH_SHORT).show()
                    Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                }
            }
            override fun onFailure(call: Call<List<CalendarResponse>>, t: Throwable) {
                Log.e("실패 $t", t.message.toString())
            }
        })
    }
    }



