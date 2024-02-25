import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.eat.databinding.FragmentChartViewRecordBinding

class ChartViewRecordFragment : Fragment() {

    private var _binding: FragmentChartViewRecordBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(
            menuName: String,
            amount: String,
            unit: String,
            mainCategory: Int
        ): ChartViewRecordFragment {
            val fragment = ChartViewRecordFragment()
            val args = Bundle()
            args.getString("menuName", menuName)
            args.getString("amount", amount)
            args.getString("unit", unit)
            args.getInt("mainCategory", mainCategory)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 차트 설정
        setPieChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setPieChart() {
        binding.chartConsumptionHistory.setUsePercentValues(true)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(30f, "탄수화물"))
        entries.add(PieEntry(40f, "단백질"))
        entries.add(PieEntry(30f, "지방"))

        val colors = mutableListOf<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())

        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueTextSize(15f)
        data.setValueTextColor(Color.BLACK)

        binding.chartConsumptionHistory.data = data
        binding.chartConsumptionHistory.description.isEnabled = false
        binding.chartConsumptionHistory.isRotationEnabled = false
        binding.chartConsumptionHistory.centerText = null
        binding.chartConsumptionHistory.setEntryLabelColor(Color.BLACK)
        binding.chartConsumptionHistory.animateY(1400, Easing.EaseInOutQuad)
        binding.chartConsumptionHistory.animate()
    }
}
