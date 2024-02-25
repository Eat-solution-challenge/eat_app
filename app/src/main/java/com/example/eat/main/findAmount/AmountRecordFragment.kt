package com.example.eat.main.findAmount

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.databinding.FragmentAmountRecordBinding
import com.example.eat.login.token
import com.example.eat.main.record.CalorieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmountRecordFragment : Fragment() {
    private val viewRecordFragment: ViewRecordFragment by lazy { ViewRecordFragment() }
    private var _binding: FragmentAmountRecordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmountRecordBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // unit spinner
        binding.spinnerUnit.adapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.unit, android.R.layout.simple_list_item_1)

        binding.buttonSearch.setOnClickListener {
            if(binding.menuname.text.isNotBlank()) {
                getCalories(binding.menuname.text.toString())
            }
            else
                Toast.makeText(requireContext(),"메뉴명을 입력해주세요.",Toast.LENGTH_SHORT).show()
        }

        binding.buttonGetLog.setOnClickListener {
            if(
                binding.menuname.text.isNotBlank() &&
                binding.kcal.text.isNotBlank() &&
                binding.textViewSubcategory.text.isNotBlank()
            ){
                toViewRecordFinishFragment() // 기록 끝
            }
            else
                Toast.makeText(requireContext(),"값을 모두 기입해주세요.",Toast.LENGTH_SHORT).show()
        }

        // Get the position argument
        val position = arguments?.getInt(ARG_GRID_ITEM_POSITION) ?: -1

        // Set the text of the TextView based on the position
        when (position) {
            0 -> binding.maincategory.text = "한식"
            1 -> binding.maincategory.text = "일식"
            2 -> binding.maincategory.text = "중식"
            3 -> binding.maincategory.text = "양식"
            4 -> binding.maincategory.text = "분식"
            5 -> binding.maincategory.text = "아시안"
            6 -> binding.maincategory.text = "간식"
            7 -> binding.maincategory.text = "디저트"
            else -> binding.maincategory.text = "기타"
        }

        when (position) {
            0 -> binding.maincategoryPicture.setImageResource(R.drawable.kor)
            1 -> binding.maincategoryPicture.setImageResource(R.drawable.jp)
            2 -> binding.maincategoryPicture.setImageResource(R.drawable.ch)
            3 -> binding.maincategoryPicture.setImageResource(R.drawable.pasta)
            4 -> binding.maincategoryPicture.setImageResource(R.drawable.korean_snack)
            5 -> binding.maincategoryPicture.setImageResource(R.drawable.asian)
            6 -> binding.maincategoryPicture.setImageResource(R.drawable.snack)
            7 -> binding.maincategoryPicture.setImageResource(R.drawable.dessert)
            else -> binding.maincategoryPicture.setImageResource(R.drawable.etc)
        }

        return rootView
    }

    private fun getCalories(menu: String) {
        // Retrofit을 사용하여 API 호출하고 응답 처리
        RetrofitAPI.getCalorieServiceInstance().getCalorieService(
            token, menu
        ).enqueue(object : Callback<List<CalorieResponse>> {
            override fun onResponse(
                call: Call<List<CalorieResponse>>,
                response: Response<List<CalorieResponse>>
            ) {
                if (response.isSuccessful) {
                    val calorieResponses = response.body()
                    if (calorieResponses != null && calorieResponses.isNotEmpty()) {
                        binding.kcal.setText(calorieResponses[0].calorie.toString())
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
    }

    private fun toViewRecordFinishFragment() {
        viewRecordFragment.arguments = createArguments()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.addToBackStack(null)
        transaction.replace(R.id.main_container, viewRecordFragment) // 변경된 컨테이너 ID를 사용합니다.
        transaction.commit()
    }

    private fun createArguments(): Bundle? {
        val args = Bundle()
        args.putString("mainCategory", binding.maincategory.text.toString())
        args.putString("subCategory", getSubcategory())
        args.putString("menuName", getMenuName())
        args.putString("unit",binding.spinnerUnit.selectedItem.toString())
        return args
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // 상위 클래스가 수행해야 하는 기본 동작 유지
        super.onSaveInstanceState(outState)
        outState.putString("subCategory", getSubcategory())
        outState.putString("menuName", getMenuName())
        outState.putInt("unit", binding.spinnerUnit.selectedItemPosition)

        Log.d(ContentValues.TAG, "!!!!OnSaveInstanceCalled!!!")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            // 메뉴 이름은 복원 안 해도 ok..??
            val subCategory = savedInstanceState.getString("subCategory")
            //val consumption = savedInstanceState.getString("consumption")
            val unitPosition = savedInstanceState.getInt("unit")
        }
    }

    companion object {
        private const val ARG_GRID_ITEM_POSITION = "grid_item_position"

        fun newInstance(gridItemPosition: Int): AmountRecordFragment {
            val fragment = AmountRecordFragment()
            val args = Bundle()
            args.putInt(ARG_GRID_ITEM_POSITION, gridItemPosition)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getMenuName(): String = binding.menuname?.text?.toString() ?: ""
    private fun getSubcategory(): String = binding.textViewSubcategory?.text?.toString() ?: ""
}
