package com.usj.news_application.repositories

import android.util.Log
import com.usj.news_application.models.News
import com.usj.news_application.network.NewsApiService

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun fetchNews(): List<News> {
        return try {
            val news = apiService.getNews()
            Log.d("NewsRepository", "Fetched ${news.size} news items")
            news
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error fetching news", e)
            throw e
        }
    }
}