package com.example.user_module

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: AccountInfoViewModel by viewModels {
        AccountInfoViewModelFactory(UserRepository(DatabaseHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        recyclerView = findViewById(R.id.recyclerViewAccountInfo)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the ViewModel for user data
        viewModel.users.observe(this, Observer { users ->
            recyclerView.adapter = AccountInfoAdapter(users)
        })

        // Load users
        viewModel.loadUsers()
    }
}
