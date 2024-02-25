import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.databinding.ItemContactsBinding
import com.example.eat.main.findAmount.Contacts

class ContactsListAdapter(private var itemList: List<Contacts>, private val fragmentActivity: FragmentActivity) : RecyclerView.Adapter<ContactsViewHolder>() {

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding, fragmentActivity)
    }

    fun setData(newItemList: List<Contacts>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }
}
