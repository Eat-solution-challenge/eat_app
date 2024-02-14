package com.example.eat.main.calendar

import android.annotation.SuppressLint
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eat.databinding.FragmentCalendarBinding
import java.io.FileOutputStream

class CalendarFragment : Fragment() {
    private val myFragment: CalendarFragment by lazy { CalendarFragment() }
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

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 달력 날짜가 선택되면
            binding.diaryTextView.visibility = View.VISIBLE // 해당 날짜가 뜨는 textView가 Visible
            binding.saveBtn.visibility = View.VISIBLE // 저장 버튼이 Visible
            binding.contextEditText.visibility = View.VISIBLE // EditText가 Visible
            binding.textView2.visibility = View.INVISIBLE // 저장된 일기 textView가 Invisible
            binding.chaBtn.visibility = View.INVISIBLE // 수정 Button이 Invisible
            binding.delBtn.visibility = View.INVISIBLE // 삭제 Button이 Invisible

            binding.diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            // 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
            binding.contextEditText.setText("") // EditText에 공백값 넣기

            checkedDay(year, month, dayOfMonth) // checkedDay 메소드 호출
        }

        binding.saveBtn.setOnClickListener { // 저장 Button이 클릭되면
            saveDiary(fname) // saveDiary 메소드 호출
            Toast.makeText(requireContext(), "$fname 데이터를 저장했습니다.", Toast.LENGTH_SHORT).show()
            // 토스트 메세지
            str = binding.contextEditText.text.toString() // str 변수에 edittext내용을 toString
            //형으로 저장
            binding.textView2.text = "${str}" // textView에 str 출력
            binding.saveBtn.visibility = View.INVISIBLE
            binding.chaBtn.visibility = View.VISIBLE
            binding.delBtn.visibility = View.VISIBLE
            binding.contextEditText.visibility = View.VISIBLE
            binding.textView2.visibility = View.INVISIBLE
        }
        return binding.root
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

            binding.contextEditText.visibility = View.INVISIBLE
            binding.textView2?.visibility = View.VISIBLE
            binding.textView2?.text = "${str}" // textView에 str 출력

            binding.saveBtn?.visibility = View.INVISIBLE
            binding.chaBtn?.visibility = View.VISIBLE
            binding.delBtn?.visibility = View.VISIBLE

            binding.chaBtn?.setOnClickListener { // 수정 버튼을 누를 시
                binding.contextEditText?.visibility = View.VISIBLE
                binding.textView2?.visibility = View.INVISIBLE
                binding.contextEditText?.setText(str) // editText에 textView에 저장된 내용을 출력
                binding.saveBtn?.visibility=VISIBLE
                binding.chaBtn?.visibility = INVISIBLE
                binding.delBtn?.visibility = INVISIBLE
                binding.textView2?.text = "${binding.contextEditText?.text}"
            }

            binding.delBtn?.setOnClickListener {
                binding.textView2?.visibility = View.INVISIBLE
                binding.contextEditText?.setText("")
                binding.contextEditText?.visibility = View.VISIBLE
                binding.saveBtn?.visibility = View.VISIBLE
                binding.chaBtn?.visibility = View.INVISIBLE
                binding.delBtn?.visibility = View.INVISIBLE
                removeDiary(fname)
                Toast.makeText(requireContext(), "$fname 데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show()

            }

            if(binding.textView2?.text == ""){
                binding.textView2?.visibility = View.INVISIBLE
                binding.diaryTextView?.visibility = View.VISIBLE
                binding.saveBtn?.visibility = View.VISIBLE
                binding.chaBtn?.visibility = View.INVISIBLE
                binding.delBtn?.visibility = View.INVISIBLE
                binding.contextEditText?.visibility = View.VISIBLE
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun saveDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = requireContext().openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = binding.contextEditText?.text.toString()
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
            fos = requireContext().openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
