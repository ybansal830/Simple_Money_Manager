package com.myfirst.simplemoneymanager.views.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myfirst.simplemoneymanager.R
import com.myfirst.simplemoneymanager.model.Money
import kotlinx.android.synthetic.main.money_item_view.view.*

class MoneyViewHolder(itemView: View, val onMoneyItemClicked: OnMoneyItemClicked)
    : RecyclerView.ViewHolder(itemView){

    public fun setData(money: Money){
        itemView.apply {
            tvAmount.text = money.amount.toString()
            if (money.category.equals("Income"))
            tvAmount.setTextColor(ContextCompat.getColor(context, R.color.teal_700))
            else tvAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
            tvDescription.text = money.description
            tvDate.text = money.date
        }
        itemView.item.setOnClickListener {
            onMoneyItemClicked.onClick(money)
        }
    }

}