package com.example.eat.main.findAmount

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.eat.R
import com.example.eat.databinding.FragmentAmountRecordBinding

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

        // Here you can access views using binding.textView1, binding.imageView3, etc.
        binding.spinnerUnit.adapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.unit, android.R.layout.simple_list_item_1)

        binding.button3.setOnClickListener {
            toViewRecordFinishFragment()// 기록 끝
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

    private fun toViewRecordFinishFragment() {
        viewRecordFragment.arguments = createArguments()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.addToBackStack(null)
        transaction.replace(R.id.main_container, viewRecordFragment) // 변경된 컨테이너 ID를 사용합니다.
        transaction.commit()
    }

    private fun createArguments(): Bundle? {
        val args = Bundle()
        // maincategory는 서버로 전송만 필요
        args.putString("subCategory", getSubcategory())
        args.putString("menuName", getMenuName())
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
