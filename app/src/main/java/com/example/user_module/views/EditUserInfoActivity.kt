package com.example.user_module.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user_module.DatabaseHelper
import com.example.user_module.R

class EditUserInfoActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_info)

        databaseHelper = DatabaseHelper(this)

        // Initialize views
        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Retrieve logged-in user email from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val loggedInEmail = sharedPreferences.getString("loggedInEmail", null)

        if (loggedInEmail != null) {
            val user = databaseHelper.getUserByEmail(loggedInEmail)
            if (user != null) {
                etFirstName.setText(user.firstName)
                etLastName.setText(user.lastName)
                etEmail.setText(user.email)
            }
        } else {
            Toast.makeText(this, "Error: User not logged in.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnSave.setOnClickListener {
            val firstName = etFirstName.text.toString().takeIf { it.isNotEmpty() }
            val lastName = etLastName.text.toString().takeIf { it.isNotEmpty() }
            val email = etEmail.text.toString().takeIf { it.isNotEmpty() }
            val newPassword = etNewPassword.text.toString().takeIf { it.isNotEmpty() }

            if (loggedInEmail != null) {
                val user = databaseHelper.getUserByEmail(loggedInEmail)
                if (user != null) {
                    // Update only fields that are filled
                    val updatedFirstName = firstName ?: user.firstName
                    val updatedLastName = lastName ?: user.lastName
                    val updatedEmail = email ?: user.email
                    val updatedPassword = newPassword ?: user.password

                    val isUpdated = databaseHelper.updateUserWithEmail(
                        user.id,
                        updatedFirstName,
                        updatedLastName,
                        updatedPassword,
                        updatedEmail
                    )

                    if (isUpdated) {
                        Toast.makeText(this, "User information updated successfully.", Toast.LENGTH_SHORT).show()

                        // Pass updated user info back to AccountInfoActivity
                        val resultIntent = Intent()
                        resultIntent.putExtra("updatedEmail", updatedEmail)
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to update user information.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
