package com.myfirst.simplemoneymanager.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfirst.simplemoneymanager.R
import com.myfirst.simplemoneymanager.model.Money

class MoneyAdapter(val list: List<Money>, val onMoneyItemClicked: OnMoneyItemClicked)
    : RecyclerView.Adapter<MoneyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.money_item_view,parent,false)
        return MoneyViewHolder(view,onMoneyItemClicked)
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}