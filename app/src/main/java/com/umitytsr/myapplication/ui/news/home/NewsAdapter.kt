package com.umitytsr.myapplication.ui.news.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.ItemRowNewsBinding

class NewsAdapter (private val newsList: List<Article>):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    inner class NewsViewHolder(private val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: Article){
            with(binding){
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(newsImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowNewsBinding.inflate(layoutInflater,parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }
}