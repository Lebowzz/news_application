package com.usj.news_application.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usj.news_application.models.News
import com.usj.news_application.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsPageViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> get() = _newsList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        // Automatically load news when ViewModel is created
        loadNews()
    }

    fun loadNews() {
        _isLoading.value = true
        _error.value = null // Reset any previous error
        viewModelScope.launch {
            try {
                val fetchedNews = repository.fetchNews()
                _newsList.value = fetchedNews
                if (fetchedNews.isEmpty()) {
                    _error.value = "No news available."
                }
            } catch (e: Exception) {
                _error.value = "Failed to load news: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
