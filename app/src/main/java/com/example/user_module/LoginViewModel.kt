package com.example.user_module

import androidx.lifecycle.ViewModel

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun validateUser(email: String, password: String): Boolean {
        return repository.validateUser(email, password)
    }
}
