package com.usj.news_application.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.usj.news_application.R

class NewsDetailPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_details_page)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        val title = intent.getStringExtra("NEWS_TITLE")
        val content = intent.getStringExtra("NEWS_CONTENT")

        findViewById<TextView>(R.id.news_title).text = title
        findViewById<TextView>(R.id.news_content).text = content
    }
}