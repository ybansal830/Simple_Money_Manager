package com.myfirst.simplemoneymanager.views.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfirst.simplemoneymanager.views.AddEditItemActivity
import com.myfirst.simplemoneymanager.R
import com.myfirst.simplemoneymanager.views.adapter.MoneyAdapter
import com.myfirst.simplemoneymanager.views.adapter.OnMoneyItemClicked
import com.myfirst.simplemoneymanager.model.Money
import com.myfirst.simplemoneymanager.viewmodels.MoneyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_income.*

@AndroidEntryPoint
class IncomeFragment : Fragment(R.layout.fragment_income), OnMoneyItemClicked {

    private var listMoney = mutableListOf<Money>()

    private val moneyViewModel: MoneyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = MoneyAdapter(listMoney,this)
        recyclerView.layoutManager = LinearLayoutManager(context)

        getDataFromDB()

    }

    /* Continuously observing live data from room database and showing it on recycler view */

    private fun getDataFromDB(){
        moneyViewModel.getAllMoney("Income").observe(viewLifecycleOwner, Observer {
            listMoney.clear()
            listMoney.addAll(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    /* On click of any item in the recycler view showing custom alert dialog box in which asking for
    * edition or deletion of particular item */

    override fun onClick(money: Money) {
        val alertDialog = AlertDialog.Builder(context).create()
        val customLayout = layoutInflater.inflate(R.layout.alert_dialog_view, null)
        alertDialog.setView(customLayout)
        alertDialog.show()
        val mBtnEdit = customLayout.findViewById<Button>(R.id.btnEdit)
        val mBtnDelete = customLayout.findViewById<Button>(R.id.btnDelete)
        mBtnEdit.setOnClickListener {
            val intent = Intent(context, AddEditItemActivity::class.java)
            intent.putExtra("category",money)
            startActivity(intent)
        }

        mBtnDelete.setOnClickListener {
            moneyViewModel.deleteMoney(money)
            alertDialog.dismiss()
        }
    }

}