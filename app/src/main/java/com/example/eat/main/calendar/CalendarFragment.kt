package com.example.eat.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat


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

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val args=Bundle()
            args.putInt("year", date.year)
            args.putInt("month", date.month)
            args.putInt("day", date.day)

            calendar2Fragment.arguments = args
            toCalendar2Fragment()
        }

        return binding.root
    }

    private fun toCalendar2Fragment() {
    // 미리 생성한 CheckRecordFragment의 arguments를 설정
       // calendar2Fragment.arguments = createArguments()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
    //main_container의 CheckRecordFragment로 transaction 한다.
        transaction.addToBackStack(null)    //back stack에 RecordFrament push
        transaction.replace(R.id.main_container, calendar2Fragment)
        transaction.commit()
    }

    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
        fname = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"
        // 저장할 파일 이름 설정. Ex) 2019-01-20.txt

        try {

            val fis = requireContext().openFileInput(fname) // fname 파일 오픈!!

            val fileData = ByteArray(fis.available()) // fileData에 파이트 형식으로 저장
            fis.read(fileData) // fileData를 읽음
            fis.close()

            str = String(fileData) // str 변수에 fileData를 저장


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    }



