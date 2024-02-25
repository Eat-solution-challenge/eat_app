import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eat.RetrofitAPI
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.eat.databinding.FragmentChartViewRecordBinding
import com.example.eat.login.token
import com.example.eat.main.findAmount.Contacts
import com.example.eat.main.findAmount.ResponseSubLog
import com.example.eat.main.record.CalorieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChartViewRecordFragment : Fragment() {

    private var _binding: FragmentChartViewRecordBinding? = null
    private val binding get() = _binding!!
    var protein:Double=0.0
    var carbs:Double=0.0
    var fat:Double=0.0
    lateinit var memo : String
    private var total : Float = 0.0f
    var carbsPercentage :Float = 0.0f
    var proteinPercentage :Float = 0.0f
    var fatPercentage : Float = 0.0f
    companion object {
        fun newInstance(
            menuName: String,
            amount: String,
            unit: String,
            mainCategory: Int,
            createdTime: String,
            carbs: Double,
            protein: Double,
            fat: Double,
            memo: String
        ): ChartViewRecordFragment {
            val fragment = ChartViewRecordFragment()
            val args = Bundle()
            args.putString("menuName", menuName)
            args.putString("amount", amount)
            args.putString("unit", unit)
            args.putString("createdTime", createdTime)
            args.putInt("mainCategory", mainCategory)
            args.putDouble("carbs", carbs)
            args.putDouble("protein", protein)
            args.putDouble("fat", fat)
            args.putString("memo", memo)

            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartViewRecordBinding.inflate(inflater, container, false)
        val args = arguments
        if (args != null) {
            val menuName = args.getString("menuName", "") ?: ""
            val amount = args.getString("amount", "") ?: ""
            val unit = args.getString("unit", "") ?: ""
            val mainCategory = args.getInt("mainCategory", 0)
            val createdTime=args.getString("createdTime","")
            val carbs=args.getDouble("carbs",0.0)
            val protein=args.getDouble("protein",0.0)
            val fat=args.getDouble("fat",0.0)
            val memo = args.getString("memo", "") ?: ""

            binding.dateTextView.setText(createdTime)
            binding.menuname2.setText(menuName)
            binding.amountTextView.setText(amount)
            binding.amountunit.setText(unit)
            binding.maincategoryPicture.setImageResource(mainCategory)
            binding.memoTextView.setText(memo)

            getNutrients(menuName)
            // 과식, 적정, 소식의 비율 계산
            total = (carbs + protein + fat).toFloat()
            carbsPercentage = (carbs.toFloat() / total) * 100
            proteinPercentage = (protein.toFloat() / total) * 100
            fatPercentage = (fat.toFloat() / total) * 100

            // 차트 설정
            setPieChart(carbsPercentage, proteinPercentage, fatPercentage)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getNutrients(menuName: String) {
        RetrofitAPI.getCalorieServiceInstance().getCalorieService(
            token, menuName
        ).enqueue(object : Callback<List<CalorieResponse>> {
            override fun onResponse(
                call: Call<List<CalorieResponse>>,
                response: Response<List<CalorieResponse>>
            ) {
                if (response.isSuccessful) {
                    val nutrientResponses = response.body()
                    if (nutrientResponses != null) {
                        val matchingMenu = nutrientResponses.find { it.menu == menuName }
                        if (matchingMenu != null) {
                            protein = matchingMenu.protein
                            fat=matchingMenu.fat
                            carbs=matchingMenu.carbs
                            memo=matchingMenu.memo
                            Log.e("데이터 로드 성공", "$protein $fat $carbs $memo")
                        }
                        }

                    } else {
                        Log.e("데이터 로드 실패", "응답 데이터가 null입니다.")
                    }
                }
            override fun onFailure(call: Call<List<CalorieResponse>>, t: Throwable) {
                Log.e("실패 $t", t.message.toString())
            }
        })
    }
    private fun setPieChart(carbsPercentage: Float, proteinPercentage: Float, fatPercentage: Float) {
        binding.chartNutrients.setUsePercentValues(true)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(carbsPercentage, "탄수화물"))
        entries.add(PieEntry(proteinPercentage, "단백질"))
        entries.add(PieEntry(fatPercentage, "지방"))

        val customColors = intArrayOf(
            Color.argb(84, 38, 80, 200),
            Color.argb(87, 76, 175, 80),
            Color.argb(89, 200, 80, 80))

        val dataSet = PieDataSet(entries, "")
        dataSet.apply {
            colors = customColors.toList()
            valueTextColor = Color.BLACK
            valueTextSize = 25f
        }
        val data = PieData(dataSet)
        data.setValueTextSize(20f)
        data.setValueTextColor(Color.BLACK)

        binding.chartNutrients.data = data
        binding.chartNutrients.description.isEnabled = false
        binding.chartNutrients.isRotationEnabled = false
        binding.chartNutrients.centerText = null
        binding.chartNutrients.setEntryLabelColor(Color.BLACK)
        binding.chartNutrients.animateY(1400, Easing.EaseInOutQuad)
        binding.chartNutrients.animate()
    }
}
