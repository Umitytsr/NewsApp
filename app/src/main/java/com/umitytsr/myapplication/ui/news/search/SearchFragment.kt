package com.umitytsr.myapplication.ui.news.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.FragmentSearchBinding
import com.umitytsr.myapplication.ui.news.home.HomeFragmentDirections
import com.umitytsr.myapplication.ui.news.home.NewsDescriptionAdapter
import com.umitytsr.myapplication.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchNews.clearFocus()
                if (query != null) {
                    viewModel.getSearchNews(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    if (newText.isNullOrEmpty()){
                        initDescriptionRecyclerView(emptyList())
                    }else{
                        viewModel.getSearchNews(newText)
                    }

                }
                return true
            }

        })
        collectData()
    }

    private fun collectData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.propertiesSearchNews.collectLatest { resource ->
                when(resource){
                    is Resource.Loading -> {
                        // Loading Indicator
                    }
                    is Resource.Success -> {
                        initDescriptionRecyclerView(resource.data)
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(),"Check your internet", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun initDescriptionRecyclerView(news: List<Article>){
        binding.searchNewsRecyclerView.adapter = getDescriptionNewsAdapter(news)
    }

    private fun getDescriptionNewsAdapter(news: List<Article>): NewsDescriptionAdapter{
        return NewsDescriptionAdapter(news){ position ->
            val newsUI = news[position]
            val action = SearchFragmentDirections.actionSearchFragmentToDetailerFragment(newsUI)
            findNavController().navigate(action)
        }
    }
}