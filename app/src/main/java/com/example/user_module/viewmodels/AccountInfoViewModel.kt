package com.example.user_module.models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.user_module.DatabaseHelper
import com.example.user_module.User
import com.example.user_module.repositories.UserRepository

class AccountInfoViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun loadUsers(context: Context) {
        val dbHelper = DatabaseHelper(context)
        val repository = UserRepository(dbHelper)
        _users.value = repository.getAllUsers()
    }
}
