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
import androidx.navigation.fragment.findNavController
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.google.android.material.tabs.TabLayout
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.FragmentHomeBinding
import com.umitytsr.myapplication.util.Resource
import com.umitytsr.myapplication.util.getDescriptionNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var carouselSnapHelper: CarouselSnapHelper? = null
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

        binding.generalNewsSeeAllButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAllNewsFragment()
            )
        }
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
                                    initGeneralNewsRecyclerView(resource.data)
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
                                    initDescriptionRecyclerView(resource.data)
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

    private fun initGeneralNewsRecyclerView(news: List<Article>){
        if (carouselSnapHelper == null) {
            carouselSnapHelper = CarouselSnapHelper().apply {
                attachToRecyclerView(binding.generalNewsRecyclerView)
            }
        }

        binding.generalNewsRecyclerView.apply {
            layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())
            adapter = getGeneralNewsAdapter(news)
        }
    }

    private fun getGeneralNewsAdapter(news: List<Article>): NewsAdapter{
        return NewsAdapter(news){ position ->
            val newsUI = news[position]
            val action = HomeFragmentDirections.actionHomeFragmentToDetailerFragment(newsUI)
            findNavController().navigate(action)
        }
    }

    private fun initDescriptionRecyclerView(news: List<Article>){
        binding.categoryRecyclerView.adapter = getDescriptionNewsAdapter(news){
            val action = HomeFragmentDirections.actionHomeFragmentToDetailerFragment(it)
            findNavController().navigate(action)
        }
    }

}