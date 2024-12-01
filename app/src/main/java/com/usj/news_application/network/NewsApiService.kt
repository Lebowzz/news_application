package com.usj.news_application.network

import retrofit2.Call
import retrofit2.http.GET
import com.usj.news_application.models.News

interface NewsApiService {
    @GET("news")
    suspend fun getNews(): List<News>
}
