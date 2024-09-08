package com.umitytsr.myapplication.ui.news.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.FragmentHomeBinding
import com.umitytsr.myapplication.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        collectData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayoutListener()
        collectData()
    }

    private fun setupTabLayoutListener(){
        with(binding){
            categoryTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val selectedTabText = tab?.text.toString().lowercase()
                    viewModel.getAllCategoryNews(selectedTabText)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
    }

    private fun collectData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                with(viewModel){
                    launch {
                        propertiesNews.collectLatest { resource ->
                            when(resource){
                                is Resource.Loading -> {
                                    // Loading Indicator
                                }
                                is Resource.Success -> {
                                    initLatestNewsRecylerView(resource.data)
                                }
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(),"Check your internet",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    launch {
                        propertiesCategoryNews.collectLatest { resource ->
                            when(resource){
                                is Resource.Loading -> {
                                    // Loading Indicator
                                }
                                is Resource.Success -> {
                                    initCategoryNewsRecylerView(resource.data)
                                }
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(),"Check your internet",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initLatestNewsRecylerView(newsList: List<Article>){
        val _adapter = NewsAdapter(newsList)
        val _layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        with(binding.latestNewsRecyclerView){
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }
    }

    private fun initCategoryNewsRecylerView(newsList: List<Article>){
        val _adapter = NewsAdapter(newsList)
        val _layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        with(binding.categoryRecyclerView){
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }
    }
}