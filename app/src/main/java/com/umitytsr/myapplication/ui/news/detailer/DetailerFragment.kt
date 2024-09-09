package com.umitytsr.myapplication.ui.news.detailer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.databinding.FragmentDetailerBinding
import com.umitytsr.myapplication.util.formatDateTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailerFragment : Fragment() {
    private lateinit var binding: FragmentDetailerBinding
    private val args: DetailerFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailerBinding.inflate(inflater,container,false)
        return binding.root
    }

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
                findNavController().navigate(
                    DetailerFragmentDirections.actionDetailerFragmentToHomeFragment()
                )
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

}