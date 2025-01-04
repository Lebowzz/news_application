package com.example.user_module.models

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthdate: String,
    val password: String // Added password field
)


