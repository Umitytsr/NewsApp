package com.umitytsr.myapplication.ui.news.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.ItemRowNewsBinding

class NewsDescriptionAdapter(
    private val newsList: List<Article>,
    private val onItemClickNewsCategory: NewsCategoryItemClickListener
    ): RecyclerView.Adapter<NewsDescriptionAdapter.NewsDescriptionViewHolder>(){

    inner class NewsDescriptionViewHolder(private val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: Article){
            with(binding){
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(newsImageView)
                newsTitleTextView.text = news.title
                newsDescriptionTextView.text = news.description

                newsCardView.setOnClickListener {
                    onItemClickNewsCategory.newsCategoryItemClicked(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDescriptionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowNewsBinding.inflate(layoutInflater,parent,false)
        return NewsDescriptionViewHolder(binding)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsDescriptionViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    interface NewsCategoryItemClickListener{
        fun newsCategoryItemClicked(news: Article)
    }
}