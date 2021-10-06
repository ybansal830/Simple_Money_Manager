package com.myfirst.simplemoneymanager.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoneyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun delete(money: Money)

    @Query("SELECT * FROM money WHERE category = :category")
    fun get(category: String) : LiveData<List<Money>>

}