package com.example.eat.main.findAmount

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.databinding.FragmentViewRecordBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewRecordFragment : Fragment() {
    private var _binding: FragmentViewRecordBinding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null
    // Record 기록 불러오기
    // ++ 적정량 계산해서 알려주기

    lateinit var subCategory : String
    lateinit var menuName : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewRecordBinding.inflate(inflater, container, false)

        args = arguments
        if (args != null) {
            subCategory = args!!.getString("subCategory", "")
            menuName = args!!.getString("menuName", "")

            binding.bfRecordedMenuname.text = menuName
            binding.bfRecordedSubcategory.text = subCategory
        }

        //적정량 찾기
        findAmount()

        binding.backbutton.setOnClickListener {
            toAmountRecordFragment()
        }
        return binding.root
    }

    fun toAmountRecordFragment() {
        val amountRecordFragment = AmountRecordFragment.newInstance(0) // 0은 해당 Fragment에 전달할 position입니다. 적절한 값을 전달하세요.
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, amountRecordFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun findAmount(){
// ProperAmountResponse 및 ProperAmountService 정의

                RetrofitAPI.getProperAmountInstance().getProperAmount(
                    subCategory
                ).enqueue(object : Callback<ProperAmountResponse> {
                    override fun onResponse(call: Call<ProperAmountResponse>, response: Response<ProperAmountResponse>) {
                        if (response.isSuccessful) {
                            val myResponse = response.body()
                            if (myResponse != null) {
                                //회원 정보 수정
                                binding.kcal.text = myResponse.properAmount.toString()
                                Log.d("데이터 로드 성공", "데이터 로드 성공")
                            } else {
                                Log.e("데이터 로드 실패", "응답 데이터가 null입니다.")
                            }
                        } else {
                            Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                        }
                    }
                    override fun onFailure(call: Call<ProperAmountResponse>, t: Throwable) {
                        Log.e("실패 $t", t.message.toString())
                    }
                })

    }

}
