package com.example.user_module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountInfoViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
