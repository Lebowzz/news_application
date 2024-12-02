package com.usj.news_application.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usj.news_application.R
import com.usj.news_application.workers.NewsNotificationWorker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Schedule periodic work
        NewsNotificationWorker.schedulePeriodicWork(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsPage())
            .commit()
    }
}
