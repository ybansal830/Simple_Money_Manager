package com.myfirst.simplemoneymanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.myfirst.simplemoneymanager.model.Money
import com.myfirst.simplemoneymanager.repository.MoneyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor(val repo: MoneyRepo): ViewModel() {

    fun addMoney(money: Money){
        repo.addMoneyToRoom(money)
    }

    fun updateMoney(money: Money){
        repo.updateMoney(money)
    }

    fun deleteMoney(money: Money){
        repo.deleteMoney(money)
    }

    fun getAllMoney(category: String): LiveData<List<Money>>{
        return repo.getAllMoney(category)
    }

}