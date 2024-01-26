package com.example.eat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.eat.databinding.RecordFragmentBinding

class RecordFragment: Fragment() {
    private var _binding: RecordFragmentBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=RecordFragmentBinding.inflate(inflater,container,false)
        binding.spinnerMainCategory.adapter=ArrayAdapter.createFromResource(requireContext(),R.array.main_category,R.layout.custom_spinner_dropdown_item)
        binding.spinnerUnit.adapter=ArrayAdapter.createFromResource(requireContext(),R.array.unit,android.R.layout.simple_list_item_1)
        return binding.root
    }

}