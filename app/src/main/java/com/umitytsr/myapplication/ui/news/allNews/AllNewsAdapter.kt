package com.umitytsr.myapplication.ui.news.allNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.databinding.ItemRowNewsBinding

class AllNewsAdapter: RecyclerView.Adapter<AllNewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: Article){
            with(binding){
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(newsImageView)
                newsTitleTextView.text = news.title
                newsDescriptionTextView.text = news.description
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
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val diffCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffCallback)

    private var onItemClickedListener: ((Article) -> Unit)? = null
}