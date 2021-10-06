package com.myfirst.simplemoneymanager.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Money::class],version = 1)
abstract class MoneyDatabase: RoomDatabase() {

    abstract fun getMoneyDAO() : MoneyDAO

    companion object {
        private var INSTANCE: MoneyDatabase? = null
        fun getDatabaseObject(context: Context?): MoneyDatabase{
            if (INSTANCE == null){
                val builder = Room.databaseBuilder(context!!.applicationContext,
                    MoneyDatabase::class.java,"money_DB")
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
            }
            return INSTANCE!!
        }
    }

}