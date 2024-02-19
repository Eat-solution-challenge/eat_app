package com.example.eat.main.findAmount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.databinding.FragmentViewRecordBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ViewRecordFragment : Fragment() {
    private var _binding: FragmentViewRecordBinding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null
    // Record 기록 불러오기
    // ++ 적정량 계산해서 알려주기

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewRecordBinding.inflate(inflater, container, false)

        args = arguments
        if (args != null) {
            val subCategory = args!!.getString("subCategory", "")
            val menuName = args!!.getString("menuName", "")

            binding.bfRecordedMenuname.text = menuName
            binding.bfRecordedSubcategory.text = subCategory
        }

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

    // 원형 차트 관련해서
    private fun setBarChart() {
        binding.chartConsumption.setUsePercentValues(true)

        // data set
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(33f, "탄수화물"))
        entries.add(PieEntry(14f, "단백질"))
        entries.add(PieEntry(33f, "지방"))
        entries.add(PieEntry(20f, "당"))

        // add a lot of colors
        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = colorsItems
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
}
