package com.umitytsr.myapplication.ui.news.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.FragmentFavoriteBinding
import com.umitytsr.myapplication.ui.news.home.HomeFragmentDirections
import com.umitytsr.myapplication.ui.news.home.NewsDescriptionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel : FavoriteFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        collectData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllNewsFavorites()
    }

    private fun collectData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.propertiesFavorite.collectLatest {
                        initDescriptionRecyclerView(it)
                    }
                }
            }
        }
    }

    private fun initDescriptionRecyclerView(news: List<Article>){
        binding.favoriteNewsRecyclerView.adapter = getDescriptionNewsAdapter(news)
    }

    private fun getDescriptionNewsAdapter(news: List<Article>): NewsDescriptionAdapter{
        return NewsDescriptionAdapter(news){ position ->
            val newsUI = news[position]
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailerFragment(newsUI)
            findNavController().navigate(action)
        }
    }
}