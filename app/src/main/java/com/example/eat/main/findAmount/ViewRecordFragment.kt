package com.example.eat.main.findAmount

import ContactsListAdapter
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
import com.example.eat.main.my.MyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewRecordFragment : Fragment() {
    private var _binding: FragmentViewRecordBinding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsListAdapter

    lateinit var mainCategory: String
    lateinit var subCategory: String
    lateinit var menuName: String
    lateinit var unit: String

    var contactsList: List<Contacts> = listOf()
    var subcategoryID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewRecordBinding.inflate(inflater, container, false)
        args = arguments
        if (args != null) {
            mainCategory = args!!.getString("mainCategory", "")
            subCategory = args!!.getString("subCategory", "")
            menuName = args!!.getString("menuName", "")
            unit = args!!.getString("unit", "")
            binding.bfRecordedMenuname.text = menuName
            binding.bfRecordedSubcategory.text = subCategory
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentViewRecordBinding.bind(view)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ContactsListAdapter(contactsList, requireActivity())
        recyclerView.adapter = adapter

        binding.backbutton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        getName()
        findAmount()
        getSubcategoryID(getCategoryID(mainCategory), subcategory = subCategory)
        getSubLog(subcategoryID)
    }

    // 서버에서 subcategoryID를 가져오는 함수
    private fun getSubcategoryID(categoryId: Int, subcategory: String) {
        RetrofitAPI.getSubcategoryIdServiceInstance().getSubcategoryID(
            token, categoryId
        ).enqueue(object : Callback<List<SubcategoryIDResponse>> {
            override fun onResponse(
                call: Call<List<SubcategoryIDResponse>>,
                response: Response<List<SubcategoryIDResponse>>
            ) {
                if (response.isSuccessful) {
                    val idResponses = response.body()
                    if (idResponses != null) {
                        val matchingSubcategory = idResponses.find { it.name == subcategory }
                        if (matchingSubcategory != null)
                            subcategoryID = matchingSubcategory.id
                        Log.d("데이터 로드 성공", "데이터 로드 성공")
                    } else {
                        Log.e("데이터 로드 실패", "응답 데이터가 null입니다.")
                    }
                } else {
                    Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<List<SubcategoryIDResponse>>, t: Throwable) {
                Log.e("실패 $t", t.message.toString())
            }
        })
    }

    // 카테고리명을 카테고리ID로 변환하는 함수
    fun getCategoryID(mainCategory: String): Int {
        return when (mainCategory) {
            "분식" -> 1
            "일식" -> 2
            "한식" -> 3
            "양식" -> 4
            "중식" -> 5
            "아시안" -> 6
            "간식" -> 7
            "디저트" -> 8
            else -> {
                Log.e("getCategoryID", "Unknown mainCategory: $mainCategory")
                0
            }
        }
    }

    private fun getSubLog(subcategoryID: Int) {
        RetrofitAPI.getSubLogServiceInstance().getLog(
            token, subcategoryID
        ).enqueue(object : Callback<List<ResponseSubLog>> {
            override fun onResponse(
                call: Call<List<ResponseSubLog>>,
                response: Response<List<ResponseSubLog>>
            ) {
                if (response.isSuccessful) {
                    val logResponses = response.body()
                    if (logResponses != null) {
                        contactsList = logResponses.map {
                            Contacts(it.menu, it.intake.toString(), it.unit, getImage(mainCategory))
                        }
                        adapter.setData(contactsList)
                        val itemCount = adapter.itemCount
                        Log.d("Item Count", "현재 아이템 수: $itemCount")
                        Log.d("데이터 로드 성공", "데이터 로드 성공")
                    } else {
                        Log.e("데이터 로드 실패", "응답 데이터가 null입니다.")
                    }
                } else {
                    Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<List<ResponseSubLog>>, t: Throwable) {
                Log.e("실패 $t", t.message.toString())
            }
        })
    }

    private fun getName() {
        RetrofitAPI.getMyServiceInstance().getMy(
            token
        ).enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    if (myResponse != null) {
                        val nickName = myResponse.nickname!!
                        binding.name.text = nickName
                        binding.name2.text = nickName
                        Log.d("데이터 로드 성공", "데이터 로드 성공")
                    } else {
                        Log.e("데이터 로드 실패", "응답 데이터가 null입니다.")
                    }
                } else {
                    Log.e("데이터 로드 실패", "응답이 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Log.e("실패 $t", t.message.toString())
            }
        })
    }

    private fun findAmount() {
        RetrofitAPI.getProperAmountInstance().getProperAmount(
            token, subCategory, unit
        ).enqueue(object : Callback<ProperAmountResponse> {
            override fun onResponse(
                call: Call<ProperAmountResponse>,
                response: Response<ProperAmountResponse>
            ) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    if (myResponse != null) {
                        binding.kcal.setText(
                            Math.round(myResponse.properAmount.toDouble())
                                .toString() + myResponse.unit
                        )
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

    private fun getImage(category: String): Int {
        return when (category) {
            "분식" -> R.drawable.korean_snack
            "일식" -> R.drawable.jp
            "한식" -> R.drawable.kor
            "양식" -> R.drawable.pasta
            "중식" -> R.drawable.ch
            "아시안" -> R.drawable.asian
            "간식" -> R.drawable.snack
            "디저트" -> R.drawable.dessert
            else -> 0
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
