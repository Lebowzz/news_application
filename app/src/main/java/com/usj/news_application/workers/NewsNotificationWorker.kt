package com.usj.news_application.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.usj.news_application.models.News
import com.usj.news_application.network.RetrofitInstance
import com.usj.news_application.repositories.NewsRepository
import com.usj.news_application.utils.NewsPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class NewsNotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    companion object {
        const val ACTION_NEWS_UPDATED = "com.usj.news_application.NEWS_UPDATED"

        fun schedulePeriodicWork(context: Context) {
            val newsWorkRequest = PeriodicWorkRequestBuilder<NewsNotificationWorker>(
                15, // Interval
                TimeUnit.MINUTES, // Changed to minutes for production
                5, // Flex interval
                TimeUnit.MINUTES
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "CheckNewNews",
                ExistingPeriodicWorkPolicy.KEEP,
                newsWorkRequest
            )
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val preferencesManager = NewsPreferencesManager(applicationContext)
        val repository = NewsRepository(RetrofitInstance.api)
        try {
            Log.d("NewsNotificationWorker", "Starting work...")

            // Fetch the latest news from the API
            val latestNews = repository.fetchNews()
            Log.d("NewsNotificationWorker", "Fetched ${latestNews.size} news items")

            // Get the last known news
            val lastKnownNews = preferencesManager.getLastKnownNews()
            Log.d("NewsNotificationWorker", "Last known news size: ${lastKnownNews.size}")

            // Find new news items
            val newNewsItems = findNewNewsItems(lastKnownNews, latestNews)
            Log.d("NewsNotificationWorker", "New news items: ${newNewsItems.size}")

            // If there are new news items, send notifications
            if (newNewsItems.isNotEmpty()) {
                // Broadcast an intent to signal news update
                val intent = Intent(ACTION_NEWS_UPDATED)
                applicationContext.sendBroadcast(intent)

                newNewsItems.forEach { newsItem ->
                    sendNewsNotification(newsItem)
                }

                preferencesManager.saveLastKnownNews(latestNews)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun findNewNewsItems(oldNews: List<News>, newNews: List<News>): List<News> {
        return newNews.filter { newItem ->
            val isNew = oldNews.none { it.id == newItem.id }
            Log.d("NewsNotificationWorker", "Checking news item: ${newItem.id}, isNew: $isNew")
            isNew
        }
    }

    private fun sendNewsNotification(news: News) {
        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        // Create notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "NEW_NEWS_CHANNEL",
                "New News Updates",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(applicationContext, "NEW_NEWS_CHANNEL")
            .setContentTitle("Breaking News: ${news.title}")
            .setContentText(news.content)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(news.content)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Show the notification with a unique ID for each news item
        notificationManager.notify(news.id.hashCode(), notification)
    }
}