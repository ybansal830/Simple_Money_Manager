package com.myfirst.simplemoneymanager.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.myfirst.simplemoneymanager.R
import com.myfirst.simplemoneymanager.model.Money
import com.myfirst.simplemoneymanager.viewmodels.MoneyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_edit_item.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddEditItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var cal = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var datePicker: DatePickerDialog.OnDateSetListener

    private val moneyViewModel: MoneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_item)

        datePickerDialog()

        // Setting and showing item list for selection of particular category.

        ArrayAdapter.createFromResource(this, R.array.category,
            android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
        }

        if (intent.getSerializableExtra("category") != null){
            editMoney()
        }
        else {
            tvDate.text = dateFormat.format(cal.time).toString()

            // On click of submit button adding new data into room database.

            btnSubmit.setOnClickListener {
                if(checkCredentials()){
                    val money = Money(spinner.selectedItem.toString(),
                        etAmount.text.toString().toFloat(),etDescription.text.toString(),
                        tvDate.text.toString())
                    moneyViewModel.addMoney(money)
                    finish()
                }
            }
        }

        // Showing date picker calendar to enter particular date on click of date text.

        tvDate.setOnClickListener {
            val mYear: Int = cal.get(Calendar.YEAR)
            val mMonth: Int = cal.get(Calendar.MONTH)
            val mDay: Int = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this,datePicker,mYear,mMonth,mDay).show()
        }

        tvBack.setOnClickListener {
            finish()
        }

    }

    /* If user clicks on edit after clicking of any of the particular item in Income or
    * Expense Fragment recycler view items then showing up all the details of particular
    * item on the ui. */

    private fun editMoney() {
        var money : Money = intent.getSerializableExtra("category") as Money
        if (money.category.equals("Expense"))
        spinner.setSelection(1)
        etAmount.setText(money.amount.toString())
        etDescription.setText(money.description)
        tvDate.text = money.date

        // On click of submit button updating data into room database.

        btnSubmit.setOnClickListener {
            if(checkCredentials()){
                money.description = etDescription.text.toString()
                money.category = spinner.selectedItem.toString()
                money.date = tvDate.text.toString()
                money.amount = etAmount.text.toString().toFloat()
                moneyViewModel.updateMoney(money)
                finishAffinity()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    /* Checking up all the credentials entered by the user before adding or updating the data
    * in the room database */

    private fun checkCredentials(): Boolean {
        if(etAmount.text.toString().isEmpty() && etDescription.text.toString().isEmpty()){
            Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun datePickerDialog(){
        datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,month)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            tvDate.text = dateFormat.format(cal.time).toString()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}