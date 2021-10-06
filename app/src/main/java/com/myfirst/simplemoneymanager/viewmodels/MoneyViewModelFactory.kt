package com.myfirst.simplemoneymanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myfirst.simplemoneymanager.repository.MoneyRepo

class MoneyViewModelFactory(val repo: MoneyRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoneyViewModel(repo) as T
    }
}