package com.example.user_module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        recyclerView = findViewById(R.id.recyclerViewAccountInfo)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = DatabaseHelper(this)

        val userList = dbHelper.getAllUsers()
        recyclerView.adapter = AccountInfoAdapter(userList)
    }
}
