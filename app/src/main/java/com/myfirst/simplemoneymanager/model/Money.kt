package com.myfirst.simplemoneymanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "money")
data class Money(@ColumnInfo(name = "category") var category: String,
                 @ColumnInfo(name = "amount") var amount: Float,
                 @ColumnInfo(name = "description") var description: String,
                 @ColumnInfo(name = "date") var date: String): Serializable {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int? = null


}