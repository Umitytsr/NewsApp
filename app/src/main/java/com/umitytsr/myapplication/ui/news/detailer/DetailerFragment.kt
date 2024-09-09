package com.umitytsr.myapplication.ui.news.detailer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.databinding.FragmentDetailerBinding
import com.umitytsr.myapplication.util.formatDateTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailerFragment : Fragment() {
    private lateinit var binding: FragmentDetailerBinding
    private val args: DetailerFragmentArgs by navArgs()
    private val viewModel: DetailerFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailerBinding.inflate(inflater,container,false)
        isFavoriteData()
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            Glide.with(requireContext())
                .load(args.article.urlToImage)
                .into(newsDetailerView)
            detailerTitleTextView.text = args.article.title
            authorTextView.text = args.article.author
            publishedAtTextView.text = args.article.publishedAt?.let { formatDateTime(it) }
            contentTextView.text = args.article.content

            arrowBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

            addNewsFavorite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    viewModel.addNewsToFavorites(args.article)
                }else{
                    viewModel.deleteNewsFromFavorites(args.article)
                }
            }

            newsWebView.webViewClient = WebViewClient()
            newsWebView.settings.javaScriptEnabled = true

            webViewButton.setOnClickListener {
                newsWebView.visibility = View.VISIBLE
                newsWebView.loadUrl(args.article.url ?: "https://www.google.com")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : androidx.activity.OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    with(binding){
                        if (newsWebView.visibility == View.VISIBLE){
                            if (newsWebView.canGoBack()){
                                newsWebView.goBack()
                            }else{
                                newsWebView.visibility = View.GONE
                            }
                        }else{
                            findNavController().navigateUp()
                        }
                    }

                }

            }
        )
    }

    private fun isFavoriteData(){
        args.article.urlToImage?.let { viewModel.isFavorite(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.isFavoriteProperties.collectLatest {
                    binding.addNewsFavorite.isChecked = it
                }
            }
        }
    }
}