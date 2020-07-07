package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.activity_add_expense.*
import java.util.ArrayList

class AddExpenseActivity : AppCompatActivity(), CallBackReturn {

    private          var day = "0"
    private lateinit var days : Array<String>

    private lateinit var listCards      : ArrayList<Card>
    private lateinit var cardSelected   : Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        CRUDController.findAll( Card(), supportFragmentManager , this, this)

        days =  resources.getStringArray( R.array.days )

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

        list_bank_for_add_expense.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView          : AdapterView<*>?,
                selectedItemView    : View?,
                position            : Int,
                id                  : Long
            ) {
                cardSelected = listCards[position]
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

        val expense     = Expense()
        expense.card    = cardSelected
        expense.dueDate = day.toString()
        expense.name    = edt_expense_name  .text.toString()
        expense.value   = edt_amount_expense.text.toString().toDouble()
        expense.repeat  = chk_repeat.isChecked

        CRUDController.create(expense, supportFragmentManager, this)
    }

    private fun inflateListBank( listBank : ArrayList<String> ) {
        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listBank
        )

        list_bank_for_add_expense.adapter = adapter
    }

    private fun getListBankName(listCard: ArrayList<Card> ) : ArrayList<String> {
        val names = ArrayList<String>()

        for ( bank in listCard ) {
            names.add( bank.name )
        }

        return names
    }

    override fun callback(list: ArrayList<Any>){
        listCards           = list as ArrayList<Card>
        val listBankName    = getListBankName( listCards )
        inflateListBank(listBankName)
    }

}
