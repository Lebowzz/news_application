package com.example.user_module.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.user_module.DatabaseHelper
import com.example.user_module.repositories.UserRepository

class SignUpViewModel : ViewModel() {

    fun registerUser(
        context: Context,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthdate: String
    ): Boolean {
        val dbHelper = DatabaseHelper(context)
        val repository = UserRepository(dbHelper)
        return repository.insertUser(firstName, lastName, email, password, birthdate)
    }
}
