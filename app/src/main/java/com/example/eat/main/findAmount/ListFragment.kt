package com.example.eat.main.findAmount

import ContactsListAdapter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.R
var subcategoryID: Int = 0

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsListAdapter

    private var mainCategory: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_record, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            mainCategory = args.getString("mainCategory", "")
            val contactsList = args?.getParcelableArrayList("contactsList", Contacts::class.java)
            setupRecyclerView(contactsList)
        }
    }

    private fun setupRecyclerView(contactsList: List<Contacts>?) {
        recyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (contactsList != null) {
            adapter = ContactsListAdapter(contactsList, requireActivity() as FragmentActivity)
            recyclerView.adapter = adapter
        }
    }
}