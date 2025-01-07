package com.usj.news_application.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.usj.news_application.R
import com.usj.news_application.models.News

class NewsAdapter(private val onItemClick: (News) -> Unit) : ListAdapter<News, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_page, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)
        Log.d("NewsAdapter", "Binding title: ${newsItem.title}")
        holder.title.text = newsItem.title
        holder.description.text = newsItem.content
        holder.location.text = newsItem.location
        holder.datetime.text = newsItem.datetime.replace('T', ' ')
        holder.itemView.setOnClickListener {
            onItemClick(newsItem) // Pass the clicked news item
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.content)
        val location: TextView = itemView.findViewById(R.id.location)
        val datetime: TextView = itemView.findViewById(R.id.datetime)
    }
}
