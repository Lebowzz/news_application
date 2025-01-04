package com.usj.news_application.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.usj.news_application.R
import com.usj.news_application.workers.NewsNotificationWorker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Schedule periodic work for notifications
        NewsNotificationWorker.schedulePeriodicWork(this)

        // Load the NewsPage fragment into the fragment container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsPage())
            .commit()
    }
}
