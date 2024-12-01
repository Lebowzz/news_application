package com.usj.news_application.repositories

import android.util.Log
import com.usj.news_application.models.News
import com.usj.news_application.network.RetrofitInstance

class NewsRepository {
    private val tag = "NewsRepository"

    suspend fun fetchNews(): List<News> {
        return try {
            val news = RetrofitInstance.api.getNews()
            Log.d(tag, "Fetched ${news.size} news items")
            news
        } catch (e: Exception) {
            Log.e(tag, "Error fetching news", e)
            throw e
        }
    }
}