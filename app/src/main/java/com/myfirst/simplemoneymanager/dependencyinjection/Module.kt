package com.myfirst.simplemoneymanager.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.myfirst.simplemoneymanager.model.MoneyDAO
import com.myfirst.simplemoneymanager.model.MoneyDatabase
import com.myfirst.simplemoneymanager.repository.MoneyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun getDAOObject(@ApplicationContext context: Context): MoneyDAO{
        return Room.databaseBuilder(context,MoneyDatabase::class.java,"money_DB")
            .build().getMoneyDAO()
    }

    @Singleton
    @Provides
    fun getRepo(moneyDAO: MoneyDAO): MoneyRepo{
        return MoneyRepo(moneyDAO)
    }

}