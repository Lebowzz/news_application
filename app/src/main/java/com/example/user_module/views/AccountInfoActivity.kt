package com.example.user_module.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user_module.DatabaseHelper
import com.example.user_module.R

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        databaseHelper = DatabaseHelper(this)

        // Initialize views
        val btnEditInfo = findViewById<Button>(R.id.btnEditInfo)
        val tvFirstName = findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = findViewById<TextView>(R.id.tvLastName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvBirthDate = findViewById<TextView>(R.id.tvBirthDate)

        // Retrieve logged-in user email from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val loggedInEmail = sharedPreferences.getString("loggedInEmail", null)

        if (loggedInEmail != null) {
            val user = databaseHelper.getUserByEmail(loggedInEmail)
            if (user != null) {
                tvFirstName.text = "First Name: ${user.firstName}"
                tvLastName.text = "Last Name: ${user.lastName}"
                tvEmail.text = "Email: ${user.email}"
                tvBirthDate.text = "Birthdate: ${user.birthdate}"
            } else {
                Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error: No user logged in.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Handle Edit Info button click
        btnEditInfo.setOnClickListener {
            val intent = Intent(this, EditUserInfoActivity::class.java)
            startActivity(intent)
        }
    }
}
