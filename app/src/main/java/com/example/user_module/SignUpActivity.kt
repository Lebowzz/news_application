package com.example.user_module

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    // Initialize ViewModel using Factory
    private val viewModel: SignUpViewModel by viewModels {
        SignUpViewModelFactory(UserRepository(DatabaseHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize UI components
        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmailSignUp)
        val etPassword = findViewById<EditText>(R.id.etPasswordSignUp)
        val etBirthDate = findViewById<EditText>(R.id.etBirthDate)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        // Handle Sign-Up
        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val birthdate = etBirthDate.text.toString()

            if (viewModel.registerUser(firstName, lastName, email, password, birthdate)) {
                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Failed to create account. Email might already exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
