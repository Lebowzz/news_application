package com.usj.news_application.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usj.news_application.R
import com.usj.news_application.viewmodels.NewsPageViewModel
import com.usj.news_application.adapters.NewsAdapter

class NewsPage : Fragment() {
    private val viewModel: NewsPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.news_recycler_view)
        val progressBar: ProgressBar    = view.findViewById(R.id.progress_bar)

        val adapter = NewsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            adapter.submitList(news)
        }

        return view
    }
}
