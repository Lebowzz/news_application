package com.usj.news_application.models

data class News(
    val id: String,
    val title: String,
    val content: String,
    val location: String,
    val datetime: String,
    val detailedDescription: String
)
