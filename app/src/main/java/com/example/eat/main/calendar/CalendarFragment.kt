package com.example.eat.main.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.example.eat.R
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CalendarFragment : Fragment() {

    var fname: String = ""
    var str: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val diaryTextView = view.findViewById<TextView>(R.id.diaryTextView)
        val save_Btn = view.findViewById<Button>(R.id.save_Btn)
        val cha_Btn = view.findViewById<Button>(R.id.cha_Btn)
        val del_Btn = view.findViewById<Button>(R.id.del_Btn)
        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val contextEditText = view.findViewById<EditText>(R.id.contextEditText)



        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 달력 날짜가 선택되면
            diaryTextView.visibility = View.VISIBLE // 해당 날짜가 뜨는 textView가 Visible
            save_Btn.visibility = View.VISIBLE // 저장 버튼이 Visible
            contextEditText.visibility = View.VISIBLE // EditText가 Visible
            textView2.visibility = View.INVISIBLE // 저장된 일기 textView가 Invisible
            cha_Btn.visibility = View.INVISIBLE // 수정 Button이 Invisible
            del_Btn.visibility = View.INVISIBLE // 삭제 Button이 Invisible

            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            // 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
            contextEditText.setText("") // EditText에 공백값 넣기

            checkedDay(year, month, dayOfMonth) // checkedDay 메소드 호출
        }

        save_Btn.setOnClickListener { // 저장 Button이 클릭되면
            saveDiary(fname) // saveDiary 메소드 호출
            Toast.makeText(requireContext(), "$fname 데이터를 저장했습니다.", Toast.LENGTH_SHORT).show()
            // 토스트 메세지
            str = contextEditText.text.toString() // str 변수에 edittext내용을 toString
            //형으로 저장
            textView2.text = "${str}" // textView에 str 출력
            save_Btn.visibility = View.INVISIBLE
            cha_Btn.visibility = View.VISIBLE
            del_Btn.visibility = View.VISIBLE
            contextEditText.visibility = View.INVISIBLE
            textView2.visibility = View.VISIBLE
        }
    }

    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
        fname = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"
        // 저장할 파일 이름 설정. Ex) 2019-01-20.txt
        var fis: FileInputStream? = null // FileStream fis 변수 설정
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val diaryTextView = view.findViewById<TextView>(R.id.diaryTextView)
        val save_Btn = view.findViewById<Button>(R.id.save_Btn)
        val cha_Btn = view.findViewById<Button>(R.id.cha_Btn)
        val del_Btn = view.findViewById<Button>(R.id.del_Btn)
        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val contextEditText = view.findViewById<EditText>(R.id.contextEditText)


        try {
            fis = requireContext().openFileInput(fname) // fname 파일 오픈!!

            val fileData = ByteArray(fis.available()) // fileData에 파이트 형식으로 저장
            fis.read(fileData) // fileData를 읽음
            fis.close()

            str = String(fileData) // str 변수에 fileData를 저장

            contextEditText.visibility = View.INVISIBLE
            textView2.visibility = View.VISIBLE
            textView2.text = "${str}" // textView에 str 출력

            save_Btn.visibility = View.INVISIBLE
            cha_Btn.visibility = View.VISIBLE
            del_Btn.visibility = View.VISIBLE

            cha_Btn.setOnClickListener { // 수정 버튼을 누를 시
                contextEditText.visibility = View.VISIBLE
                textView2.visibility = View.INVISIBLE
                contextEditText.setText(str) // editText에 textView에 저장된 내용을 출력
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                textView2.text = "${contextEditText.text}"
            }

            del_Btn.setOnClickListener {
                textView2.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                removeDiary(fname)
                Toast.makeText(requireContext(), "$fname 데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show()

            }

            if(textView2.text == ""){
                textView2.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                contextEditText.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun saveDiary(readyDay: String) {
        var fos: FileOutputStream? = null
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val diaryTextView = view.findViewById<TextView>(R.id.diaryTextView)
        val save_Btn = view.findViewById<Button>(R.id.save_Btn)
        val cha_Btn = view.findViewById<Button>(R.id.cha_Btn)
        val del_Btn = view.findViewById<Button>(R.id.del_Btn)
        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val contextEditText = view.findViewById<EditText>(R.id.contextEditText)


        try {
            fos = requireContext().openFileOutput(readyDay, FileOutputStream.MODE_NO_LOCALIZED_COLLATORS)
            var content: String = contextEditText.text.toString()
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun removeDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = requireContext().openFileOutput(readyDay, FileOutputStream.MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
