package com.example.eat.main.my

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.RetrofitAPI
import com.example.eat.RetrofitAPI.getMyServiceInstance
import com.example.eat.databinding.FragmentMyBinding
import com.example.eat.login.token
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var userName:String=""
private var gender:String=""
private var age: Int = 0
private var weight: Long = 0
private var height: Long = 0
private var overEat: Long = 0
private var properEat:Long = 0
private var lightEat: Long =0
private var thisWeekTrashAmount:Double=0.0
private var lastWeekTrashAmount:Double=0.0

class MyFragment: Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    private val myService: MyService by lazy {
        RetrofitAPI.getMyServiceInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        work()
        return binding.root
    }

    private fun work() {
        binding.chartConsumption.setUsePercentValues(true)

        getMyServiceInstance().getMy(
            token
        ).enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    val myResponse = response.body()
                    if (myResponse != null) {
                        val nickName = myResponse.nickname!!
                        userName = myResponse.userName!!
                        gender = myResponse.gender!!
                        age = myResponse.age!!
                        weight = myResponse.weight!!
                        height = myResponse.height!!
                        overEat = myResponse.overEat!!
                        properEat = myResponse.properEat!!
                        lightEat = myResponse.lightEat!!
                        thisWeekTrashAmount=myResponse.thisWeekTrashAmount!!
                        lastWeekTrashAmount=myResponse.lastWeekTrashAmount!!

                        //회원 정보 수정
                        binding.textName.text= nickName
                        binding.textGenderAge.text=gender+"/ 만 "+age+"세"
                        binding.textHeightWeight.text=height.toString()+"cm/"+ weight+"세"

                        //폐기물 양 비교 문구 수정
                        var change= lastWeekTrashAmount-thisWeekTrashAmount
                        if(change>0)
                            binding.textWaste.text="지난 주보다 $change L 적은 음식 폐기물이 발생했습니다!"
                        else if(change<0) {
                            change=thisWeekTrashAmount-lastWeekTrashAmount
                            binding.textWaste.text = "지난 주보다 $change L 많은 음식 폐기물이 발생했습니다."
                            }
                        else
                            binding.textWaste.text="발생한 음식 폐기물의 양이 지난 주와 동일 합니다."

                        Log.d("데이터 로드 성공", "데이터 로드 성공")

                        // 과식, 적정, 소식의 비율 계산
                        val total = overEat + properEat + lightEat
                        val overEatPercentage = (overEat.toFloat() / total) * 100
                        val properEatPercentage = (properEat.toFloat() / total) * 100
                        val lightEatPercentage = (lightEat.toFloat() / total) * 100

                        // 그래프 업데이트
                        updateChart(overEatPercentage, properEatPercentage, lightEatPercentage)
                        // 음식물쓰레기 차트 업데이트
                        setBarChart()
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

    private fun updateChart(overEatPercentage: Float, properEatPercentage: Float, lightEatPercentage: Float) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(overEatPercentage, "과식"))
        entries.add(PieEntry(properEatPercentage, "적정"))
        entries.add(PieEntry(lightEatPercentage, "소식"))

        val customColors = intArrayOf(
            Color.argb(84, 255, 76, 63),
            Color.argb(87, 76, 175, 80),
            Color.argb(89, 251, 236, 103))

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = customColors.toList()
            valueTextColor = Color.BLACK
            valueTextSize = 18f
        }

        val pieData = PieData(pieDataSet)
        binding.chartConsumption.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = null
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }
    private fun setBarChart() {
        binding.chartWaste.description.isEnabled = false
        binding.chartWaste.legend.isEnabled = false

        // 이번 주와 지난 주 쓰레기 양 데이터 설정
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, lastWeekTrashAmount.toFloat()))
        entries.add(BarEntry(1f, thisWeekTrashAmount.toFloat()))

        // 바 차트 데이터 설정
        val barDataSet = BarDataSet(entries, "").apply {
            colors = mutableListOf(Color.parseColor("#607D8B"), Color.parseColor("#FF5722")) // 각각 지난 주와 이번 주의 색상
        }

        val barData = BarData(barDataSet)
        binding.chartWaste.data = barData

        // 바 차트 축 설정
        binding.chartWaste.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(arrayOf("지난 주", "이번 주"))
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(false)
            granularity = 1f
        }

        binding.chartWaste.invalidate()
    }
}
