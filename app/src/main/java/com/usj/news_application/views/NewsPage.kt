package com.usj.news_application.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.usj.news_application.R
import com.usj.news_application.adapters.NewsAdapter
import com.usj.news_application.viewmodels.NewsPageViewModel
import com.usj.news_application.workers.NewsNotificationWorker

class NewsPage : Fragment() {
    private val viewModel: NewsPageViewModel by viewModels()
    private lateinit var newsUpdateReceiver: BroadcastReceiver
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page, container, false)

        // Initialize the toolbar
        val toolbar: MaterialToolbar = view.findViewById(R.id.topAppBar)
        toolbar.title = getString(R.string.news_page_name)

        // Initialize the "I" Button
        val userInfoButton: Button = toolbar.findViewById(R.id.userInfoButton)
        userInfoButton.setOnClickListener {
            val intent = Intent(requireContext(), AccountInfoActivity::class.java)
            startActivity(intent)
        }

        // Initialize RecyclerView and ProgressBar
        val recyclerView: RecyclerView = view.findViewById(R.id.news_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        val adapter = NewsAdapter { selectedNews ->
            val intent = Intent(requireContext(), NewsDetailPage::class.java).apply {
                putExtra("NEWS_TITLE", selectedNews.title)
                putExtra("NEWS_CONTENT", selectedNews.content)
                putExtra("NEWS_LOCATION", selectedNews.location)
                putExtra("NEWS_DATETIME", selectedNews.datetime)
                putExtra("NEWS_DETAILED_DESCRIPTION", selectedNews.detailedDescription)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observers for loading and data
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            adapter.submitList(news)
        }

        // Swipe-to-Refresh
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadNews()
        }

        // Broadcast Receiver for updates
        newsUpdateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == NewsNotificationWorker.ACTION_NEWS_UPDATED) {
                    viewModel.loadNews() // Refresh news
                }
            }
        }
        requireContext().registerReceiver(
            newsUpdateReceiver,
            IntentFilter(NewsNotificationWorker.ACTION_NEWS_UPDATED)
        )

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(newsUpdateReceiver)
    }
}
