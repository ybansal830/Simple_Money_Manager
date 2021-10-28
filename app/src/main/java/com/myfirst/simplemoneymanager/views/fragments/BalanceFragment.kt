package com.myfirst.simplemoneymanager.views.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.myfirst.simplemoneymanager.R
import com.myfirst.simplemoneymanager.model.Money
import com.myfirst.simplemoneymanager.model.MoneyDatabase
import com.myfirst.simplemoneymanager.repository.MoneyRepo
import com.myfirst.simplemoneymanager.viewmodels.MoneyViewModel
import com.myfirst.simplemoneymanager.viewmodels.MoneyViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_balance.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BalanceFragment : Fragment(R.layout.fragment_balance) {

    private var cal = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var datePickerStart: DatePickerDialog.OnDateSetListener
    private lateinit var datePickerEnd: DatePickerDialog.OnDateSetListener

    private var listIncome = mutableListOf<Money>()
    private var listExpense = mutableListOf<Money>()

    private val moneyViewModel: MoneyViewModel by viewModels()

    private var totalIncome = 0F
    private var totalExpense = 0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePickerDialog()

        setData()

        // Showing date picker calendar to enter particular date on click of start date text.

        tvStartDate.setOnClickListener {
            val mYear = cal.get(Calendar.YEAR)
            val mMonth = cal.get(Calendar.MONTH)
            val mDay = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(),datePickerStart,mYear,mMonth,mDay).show()
        }

        // Showing date picker calendar to enter particular date on click of end date text.

        tvEndDate.setOnClickListener {
            val mYear = cal.get(Calendar.YEAR)
            val mMonth = cal.get(Calendar.MONTH)
            val mDay = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(),datePickerEnd,mYear,mMonth,mDay).show()
        }

        /* On click of go button categorise the total amount of income and expense list data
        * according to user entered start and end date, and then showing the data on ui. */

        btnGo.setOnClickListener {
            totalIncome = 0F
            totalExpense = 0F
            var dateStart = dateFormat.parse(tvStartDate.text.toString())
            var dateEnd =  dateFormat.parse(tvEndDate.text.toString())
            for (i in listIncome){
                var dateCheck = dateFormat.parse(i.date)
                if (dateCheck.compareTo(dateStart) >= 0 && dateCheck.compareTo(dateEnd) <= 0)
                    totalIncome += i.amount
            }
            for (i in listExpense){
                var dateCheck = dateFormat.parse(i.date)
                if (dateCheck.compareTo(dateStart) >= 0 && dateCheck.compareTo(dateEnd) <= 0)
                    totalExpense += i.amount
            }
            tvIncomeC.text = "Income:     $totalIncome"
            tvExpenseC.text = "Expense:   $totalExpense"
            tvTotalC.text = "Total:         ${totalIncome-totalExpense}"
        }

    }

    /* Setting total income and expense data and showing it on the ui by fetching all data
    * from room database. */

    private fun setData() {

        tvStartDate.text = dateFormat.format(cal.time).toString()
        tvEndDate.text = dateFormat.format(cal.time).toString()

        moneyViewModel.getAllMoney("Income").observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
            listIncome.clear()
            listIncome.addAll(it)
            updateTotalBalance()
        })

        moneyViewModel.getAllMoney("Expense").observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
            listExpense.clear()
            listExpense.addAll(it)
            updateTotalBalance()
        })

    }

    private fun updateTotalBalance(){
        totalIncome = 0F
        totalExpense = 0F
        for (i in listIncome)
            totalIncome += i.amount
        for (i in listExpense)
            totalExpense += i.amount

        tvIncome.text = "Income:     $totalIncome"
        tvExpense.text = "Expense:   $totalExpense"
        tvTotal.text = "Total:         ${totalIncome-totalExpense}"
    }

    private fun datePickerDialog() {
        datePickerStart = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,month)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            tvStartDate.text = dateFormat.format(cal.time).toString()
        }
        datePickerEnd = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,month)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            tvEndDate.text = dateFormat.format(cal.time).toString()
        }
    }

}