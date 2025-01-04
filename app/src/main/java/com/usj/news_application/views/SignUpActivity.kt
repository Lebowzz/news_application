package com.usj.news_application.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.usj.news_application.R
import com.usj.news_application.viewmodels.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmailSignUp)
        val etPassword = findViewById<EditText>(R.id.etPasswordSignUp)
        val etBirthDate = findViewById<EditText>(R.id.etBirthDate)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val birthdate = etBirthDate.text.toString()

            if (viewModel.registerUser(this, firstName, lastName, email, password, birthdate)) {
                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Failed to create account. Email might already exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
