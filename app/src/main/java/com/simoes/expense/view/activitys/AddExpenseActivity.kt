package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.simoes.expense.R
import kotlinx.android.synthetic.main.activity_add_expense.*

class AddExpenseActivity : AppCompatActivity() {

    private          var day = 0
    private lateinit var days : IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        days = resources.getIntArray(R.array.days)

        ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item)
            .also {adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                list_day.adapter = adapter
            }

        list_day.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView          : AdapterView<*>?,
                selectedItemView    : View?,
                position            : Int,
                id                  : Long
            ) {
                day = days[ position ]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                //TODO implements
            }
        }

        btn_save_bank.setOnClickListener {
            saveExpense()
        }

    }


    private fun saveExpense() {

    }

}
