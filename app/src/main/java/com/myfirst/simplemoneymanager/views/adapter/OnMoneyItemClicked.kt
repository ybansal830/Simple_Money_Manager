package com.myfirst.simplemoneymanager.views.adapter

import com.myfirst.simplemoneymanager.model.Money

interface OnMoneyItemClicked {

    fun onClick(money: Money)

}