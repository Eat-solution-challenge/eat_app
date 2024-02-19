package com.example.eat.main.findAmount

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.R
import com.example.eat.databinding.FragmentFindAmountBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.eat.databinding.DialogLayoutBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Import the AmountRecordFragment class
import com.example.eat.main.findAmount.AmountRecordFragment

class FindAmountFragment: Fragment(), Interaction, GridRecyclerViewAdapter.OnItemClickListener {

    private lateinit var viewPagerAdapter: FindAmountViewPagerAdapter
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter
    private lateinit var viewModel: FIndAmountViewModel
    private var _binding: FragmentFindAmountBinding? = null
    private val binding get() = _binding!!
    private var isRunning = true

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): FindAmountFragment {
            val fragment = FindAmountFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindAmountBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // ViewModel 초기화 및 옵저버 등록
        viewModel = ViewModelProvider(this).get(FIndAmountViewModel::class.java)

        viewModel.setBannerItems(BannerItemList)
        viewModel.setGridItems(GridItemList)

        initViewPager2() // ViewPager2 초기화를 onCreateView에서 호출하도록 이동
        initRecyclerView()
        subscribeObservers()
        autoScrollViewPager()

        //쓰레기양 입력 버튼
        binding.aboutGarbage.setOnClickListener {
            setDialog()
        }


        // RecyclerView의 아이템 클릭 리스너 설정
        gridRecyclerViewAdapter.setOnItemClickListener(object : GridRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // RecyclerView의 아이템이 클릭되었을 때 실행되는 코드를 여기에 추가
                // 예: 원하는 동작을 수행하거나 다음 화면으로 이동하는 등의 작업을 수행
                // 예를 들어, 클릭된 아이템의 위치를 로그로 출력하려면 다음과 같이 작성할 수 있습니다.
                Log.d("RecyclerView", "Clicked item at position $position")

                // 클릭된 아이템의 위치에 해당하는 AmountRecordFragment를 띄웁니다.
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.main_container, AmountRecordFragment.newInstance(position))
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        return rootView
    }

    //음식물 쓰레기 입력 팝업창 설정
    private fun setDialog(){
        val dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root

        val ad: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        ad.setIcon(R.drawable.garbage_icon)
        ad.setView(dialogView)

        ad.setPositiveButton("입력 완료") { dialog, which ->
            val result: String = dialogBinding.editText.text.toString()
            // 서버에 저장
            dialog.dismiss()
        }

        ad.setNegativeButton("돌아가기") { dialog, which ->
            dialog.dismiss()
        }
        ad.show()

    }

    private fun initViewPager2() {
        viewPagerAdapter = FindAmountViewPagerAdapter(this@FindAmountFragment)
        val viewPager2: ViewPager2 = binding.viewPager2 // binding을 통해 View 참조 가져오도록 수정
        viewPager2.adapter = viewPagerAdapter // adapter를 설정하도록 수정

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                isRunning = true
                viewModel.setCurrentPosition(position)
            }
        })
    }

    private fun initRecyclerView() {
        binding.gridRecyclerView.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = gridRecyclerViewAdapter

            gridRecyclerViewAdapter.setOnItemClickListener(this@FindAmountFragment)
        }
        // RecyclerView에 아이템 간격 설정 적용
        val spacingInPixels = resources.dpToPx(15) // 20dp를 픽셀 단위로 변환하여 사용
        binding.gridRecyclerView.addItemDecoration(GridRecyclerViewAdapter.GridSpacingItemDecoration(spacingInPixels))

    }
    // dp를 픽셀 단위로 변환하는 확장 함수
    private fun Resources.dpToPx(dp: Int): Int {
        val density = displayMetrics.density
        return (dp * density).toInt()
    }
    private fun applyItemSpacing(recyclerView: RecyclerView, spacing: Int) {
        recyclerView.addItemDecoration(GridRecyclerViewAdapter.GridSpacingItemDecoration(spacing))
    }
    private fun subscribeObservers() {
        viewModel.bannerItemList.observe(viewLifecycleOwner, Observer { bannerItemList ->
            viewPagerAdapter.submitList(bannerItemList)
        })

        viewModel.gridItemList.observe(viewLifecycleOwner, { gridItemList ->
            gridRecyclerViewAdapter.submitList(gridItemList)
        })

        viewModel.currentPosition.observe(viewLifecycleOwner, Observer { currentPosition ->
            binding.viewPager2.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            whenResumed {
                while (isRunning) {
                    delay(3000)
                    viewModel.getCurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 5)
                    }
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, AmountRecordFragment.newInstance(position))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // binding 해제
    }

    override fun onBannerItemClicked(bannerItem: BannerItem) {
    }

    override fun onClick(v: View?) {
    }


}
