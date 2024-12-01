package com.usj.news_application.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usj.news_application.models.News

class NewsPreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("NewsPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveLastKnownNews(newsList: List<News>) {
        val newsJson = gson.toJson(newsList)
        prefs.edit().putString("LAST_KNOWN_NEWS", newsJson).apply()
    }

    fun getLastKnownNews(): List<News> {
        val newsJson = prefs.getString("LAST_KNOWN_NEWS", null)
        return if (newsJson != null) {
            val type = object : TypeToken<List<News>>() {}.type
            gson.fromJson(newsJson, type)
        } else {
            emptyList()
        }
    }

    fun clearLastKnownNews() {
        prefs.edit().remove("LAST_KNOWN_NEWS").apply()
    }
}