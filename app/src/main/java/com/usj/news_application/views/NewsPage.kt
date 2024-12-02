package com.usj.news_application.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.usj.news_application.R
import com.usj.news_application.viewmodels.NewsPageViewModel
import com.usj.news_application.adapters.NewsAdapter
import com.usj.news_application.workers.NewsNotificationWorker

class NewsPage : Fragment() {
    private val viewModel: NewsPageViewModel by viewModels()
    private lateinit var newsUpdateReceiver: BroadcastReceiver
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.news_recycler_view)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        val adapter = NewsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            adapter.submitList(news)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadNews()
        }

        newsUpdateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == NewsNotificationWorker.ACTION_NEWS_UPDATED) {
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