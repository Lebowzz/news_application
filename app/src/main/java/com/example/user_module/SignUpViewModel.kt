package com.example.user_module

import androidx.lifecycle.ViewModel

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthdate: String
    ): Boolean {
        return repository.insertUser(firstName, lastName, email, password, birthdate)
    }
}
