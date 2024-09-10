package com.umitytsr.myapplication.ui.news.allNews

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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.FragmentAllNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllNewsFragment : Fragment() {
    private lateinit var binding: FragmentAllNewsBinding
    private val viewModel: AllNewsFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllNewsBinding.inflate(inflater,container,false)
        collectData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun collectData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.propertiesNewsPaging.collectLatest {
                    initPagingRecyclerView(it)
                }
            }
        }
    }

    private fun initPagingRecyclerView(news: PagingData<Article>){
        val _adapter = AllNewsAdapter(){ article ->
            val action = AllNewsFragmentDirections.actionAllNewsFragmentToDetailerFragment(article)
            findNavController().navigate(action)
        }
        binding.allNewsRecyclerView.adapter = _adapter
        _adapter.addLoadStateListener {
            addLoadStateListener(it)
        }
        _adapter.submitData(lifecycle, news)
    }

    fun addLoadStateListener(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Loading ||
            loadState.append is LoadState.Loading
        )
        else {
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}