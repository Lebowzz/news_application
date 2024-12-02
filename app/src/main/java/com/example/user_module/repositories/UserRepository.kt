package com.example.user_module

class UserRepository(private val databaseHelper: DatabaseHelper) {

    fun getAllUsers(): List<User> {
        return databaseHelper.getAllUsers()
    }

    fun validateUser(email: String, password: String): Boolean {
        return databaseHelper.validateUser(email, password)
    }

    fun insertUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        birthdate: String
    ): Boolean {
        return databaseHelper.insertUser(firstName, lastName, email, password, birthdate)
    }
}


