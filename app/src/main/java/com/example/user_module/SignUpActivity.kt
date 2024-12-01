package com.example.user_module

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbHelper = DatabaseHelper(this)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmailSignUp)
        val etPassword = findViewById<EditText>(R.id.etPasswordSignUp)
        val etBirthDate = findViewById<EditText>(R.id.etBirthDate)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val birthdate = etBirthDate.text.toString()

            if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && birthdate.isNotBlank()) {
                val isInserted = dbHelper.insertUser(firstName, lastName, email, password, birthdate)
                if (isInserted) {
                    Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed to create account. Email might already exist.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
