package com.umitytsr.myapplication.ui.news.allNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.ItemRowNewsBinding

class AllNewsAdapter(private val onClickNews: (Article) -> Unit): PagingDataAdapter<Article,AllNewsAdapter.NewsViewHolder>(NEWS_COMPARATOR) {

    inner class NewsViewHolder(private val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: Article){
            with(binding){
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(newsImageView)
                newsTitleTextView.text = news.title
                newsDescriptionTextView.text = news.description
                newsCardView.setOnClickListener {
                    onClickNews(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllNewsAdapter.NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowNewsBinding.inflate(layoutInflater,parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllNewsAdapter.NewsViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                // Id is unique.
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}