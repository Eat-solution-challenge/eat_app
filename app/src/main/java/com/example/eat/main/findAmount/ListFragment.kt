package com.example.eat.main.findAmount

import ContactsListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eat.R

class ListFragment : Fragment() {

    val contactsList : List<Contacts> = listOf(
        Contacts("john","1/4통", "2월 19일", R.drawable.kor),
        Contacts("mir","350kcal", "3월 20일", R.drawable.jp),
        Contacts("delp", "010-3333-4444", "2월 20일", R.drawable.ch),
        Contacts("jacob", "010-3333-5555","2월 20일", R.drawable.pasta),
        Contacts("sheu", "010-3333-6666","2월 20일", R.drawable.asian),
        Contacts("ma", "010-3333-7777","2월 20일", R.drawable.snack),
        Contacts("ham", "010-3333-8889","2월 20일", R.drawable.korean_snack)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 여기서 인플레이트하거나 생성합니다.
        return inflater.inflate(R.layout.fragment_view_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 참조
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        // RecyclerView 레이아웃 매니저 설정
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 어댑터 생성 및 RecyclerView에 연결
        val adapter = ContactsListAdapter(contactsList, requireActivity() as FragmentActivity)
        recyclerView.adapter = adapter
    }


}