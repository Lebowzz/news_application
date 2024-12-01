package com.usj.news_application.models

import com.google.gson.annotations.SerializedName

data class News(
    val id: String,
    val title: String,
    val content: String,
    val location: String,
    val datetime: String,
    @SerializedName("detailed_description")
    val detailedDescription: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
