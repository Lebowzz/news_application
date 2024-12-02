package com.example.user_module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user_module.adapters.AccountInfoAdapter
import com.example.user_module.viewmodels.AccountInfoViewModel

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AccountInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        recyclerView = findViewById(R.id.recyclerViewAccountInfo)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(AccountInfoViewModel::class.java)

        // Observe the ViewModel
        viewModel.loadUsers(this) // Pass the context to fetch data from the database
        viewModel.users.observe(this) { users ->
            recyclerView.adapter = AccountInfoAdapter(users)
        }
    }
}
