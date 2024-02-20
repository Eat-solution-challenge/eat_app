import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.eat.R
import com.example.eat.databinding.FragmentViewRecordBinding
import com.example.eat.main.findAmount.AmountRecordFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.main.findAmount.Contacts

class ViewRecordFragment : Fragment() {

    private var _binding: FragmentViewRecordBinding? = null
    private val binding get() = _binding!!
    private var args: Bundle? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsListAdapter // ContactsListAdapter 임포트 필요

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewRecordBinding.inflate(inflater, container, false)
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
            val subCategory = args!!.getString("subCategory", "")
            val menuName = args!!.getString("menuName", "")

            binding.bfRecordedMenuname.text = menuName
            binding.bfRecordedSubcategory.text = subCategory
        }

        binding.backbutton.setOnClickListener {
            toAmountRecordFragment()
        }
    }

    private fun toAmountRecordFragment() {
        val amountRecordFragment = AmountRecordFragment.newInstance(0)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, amountRecordFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
