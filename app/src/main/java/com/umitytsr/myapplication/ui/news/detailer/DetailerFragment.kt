package com.umitytsr.myapplication.ui.news.detailer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umitytsr.myapplication.databinding.FragmentDetailerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailerFragment : Fragment() {
    private lateinit var binding: FragmentDetailerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailerBinding.inflate(inflater,container,false)
        return binding.root
    }
}