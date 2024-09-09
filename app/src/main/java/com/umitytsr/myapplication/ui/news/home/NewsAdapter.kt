package com.umitytsr.myapplication.ui.news.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.ItemRowNewsTitleBinding

class NewsAdapter(
    private val newsList: List<Article>,
    private val onItemClickNews: NewsItemClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemRowNewsTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Article) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(newsImageView)
                newsTitleTextView.text = news.title
                newsCardView.setOnClickListener {
                    onItemClickNews.newsItemClicked(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowNewsTitleBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    interface NewsItemClickListener{
        fun newsItemClicked(news: Article)
    }
}