import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.R
import com.example.eat.databinding.ItemContactsBinding
import com.example.eat.main.findAmount.Contacts

class ContactsViewHolder(private val binding: ItemContactsBinding, private val fragmentActivity: FragmentActivity) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Contacts) {
        binding.menuname.text = item.menuName ?: "" // Nullable String 처리
        binding.amountTextView.text = item.amount ?: "" // Nullable String 처리
        binding.dateTextView.text = item.createDateStr ?: "" // Nullable String 처리
        binding.maincategoryPicture.setImageResource(item.mainCategory)

        binding.root.setOnClickListener {
            // Fragment 전환
            val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val fragment = ChartViewRecordFragment.newInstance(
                item.menuName ?: "", // 메뉴 이름
                item.amount ?: "", // 수량
                item.createDateStr ?: "", // 날짜
                item.mainCategory // 메인 카테고리 이미지 리소스 ID
            )
            fragmentTransaction.replace(R.id.main_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
    }
}
