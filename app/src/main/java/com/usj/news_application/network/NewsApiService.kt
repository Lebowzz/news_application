package com.usj.news_application.network

import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {
    @GET("news")
    fun getNews(): Call<List<News>>
}
