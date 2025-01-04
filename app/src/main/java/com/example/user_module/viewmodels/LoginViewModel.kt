package com.example.user_module.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.user_module.DatabaseHelper
import com.example.user_module.repositories.UserRepository

class LoginViewModel : ViewModel() {

    fun validateUser(context: Context, email: String, password: String): Boolean {
        val dbHelper = DatabaseHelper(context)
        val repository = UserRepository(dbHelper)
        return repository.validateUser(email, password)
    }
}
