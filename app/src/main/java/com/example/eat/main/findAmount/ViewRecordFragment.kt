package com.example.eat.main.findAmount

import ContactsListAdapter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.databinding.FragmentViewRecordBinding
import com.example.eat.login.token
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsListAdapter // ContactsListAdapter 임포트 필요


    lateinit var subCategory : String
    lateinit var menuName : String
    lateinit var unit : String

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
            unit = args!!.getString("unit", "")

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 참조
        recyclerView = binding.recyclerView

        // RecyclerView 레이아웃 매니저 설정
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 어댑터 생성 및 RecyclerView에 연결
        val contactsList = getListOfContacts() // Contacts 리스트를 가져오는 함수 호출adapter = ContactsListAdapter(contactsList, requireActivity() as FragmentActivity)
        adapter = ContactsListAdapter(contactsList, requireActivity() as FragmentActivity)
        recyclerView.adapter = adapter

        args = arguments
        if (args != null) {
            subCategory = args!!.getString("subCategory", "")
            menuName = args!!.getString("menuName", "")

            binding.bfRecordedMenuname.text = menuName
            binding.bfRecordedSubcategory.text = subCategory
        }

        binding.backbutton.setOnClickListener {
            toAmountRecordFragment()
        }
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
                    token,subCategory,unit
                ).enqueue(object : Callback<ProperAmountResponse> {
                    override fun onResponse(call: Call<ProperAmountResponse>, response: Response<ProperAmountResponse>) {
                        if (response.isSuccessful) {
                            val myResponse = response.body()
                            if (myResponse != null) {
                                //회원 정보 수정
                                binding.kcal.text = myResponse.properAmount
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
    // Contacts 리스트를 가져오는 함수
    private fun getListOfContacts(): List<Contacts> {
        return listOf(
            Contacts("john", "1/4통", "2월 19일", R.drawable.kor),
            Contacts("mir", "350kcal", "3월 20일", R.drawable.jp),
            Contacts("delp", "010-3333-4444", "2월 20일", R.drawable.ch),
            Contacts("jacob", "010-3333-5555", "2월 20일", R.drawable.pasta),
            Contacts("sheu", "010-3333-6666", "2월 20일", R.drawable.asian),
            Contacts("ma", "010-3333-7777", "2월 20일", R.drawable.snack),
            Contacts("ham", "010-3333-8889", "2월 20일", R.drawable.korean_snack)
        )
    }

}
