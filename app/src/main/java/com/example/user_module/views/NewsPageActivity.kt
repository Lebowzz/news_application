package com.example.user_module.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user_module.R

class NewsPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_page)

        // Find buttons for navigation
        val btnLoginPage = findViewById<Button>(R.id.btnLoginPage)
        val btnDisplayInfoPage = findViewById<Button>(R.id.btnDisplayInfoPage)

        // Admin authentication views
        val etAdminUsername = findViewById<EditText>(R.id.etAdminUsername)
        val etAdminPassword = findViewById<EditText>(R.id.etAdminPassword)
        val btnAdminLogin = findViewById<Button>(R.id.btnAdminLogin)

        // Navigate to Login Page
        btnLoginPage.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Navigate to Display Info Page
        btnDisplayInfoPage.setOnClickListener {
            startActivity(Intent(this, AccountInfoActivity::class.java))
        }

        // Admin login authentication
        btnAdminLogin.setOnClickListener {
            val username = etAdminUsername.text.toString()
            val password = etAdminPassword.text.toString()

            if (username == "admin" && password == "admin") {
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminDashboardActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid Admin Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
