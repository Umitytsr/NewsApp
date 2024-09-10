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
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.FragmentFavoriteBinding
import com.umitytsr.myapplication.util.getDescriptionNewsAdapter
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
                        if (it.isNotEmpty()){
                            with(binding){
                                emtyListImageView.visibility = View.INVISIBLE
                                emptyListTextView.visibility = View.INVISIBLE
                                favoriteNewsRecyclerView.visibility = View.VISIBLE
                            }
                            initDescriptionRecyclerView(it)
                        }else{
                            with(binding){
                                emtyListImageView.visibility = View.VISIBLE
                                emptyListTextView.visibility= View.VISIBLE
                                favoriteNewsRecyclerView.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initDescriptionRecyclerView(news: List<Article>){
        binding.favoriteNewsRecyclerView.adapter = getDescriptionNewsAdapter(news){
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailerFragment(it)
            findNavController().navigate(action)
        }
    }
}