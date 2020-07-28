package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
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

        val cardToAddExpense = intent.getSerializableExtra( Helper.UUIDCARD )

        if ( cardToAddExpense == null ) {
            buildScreenWithoutParameterCard()
            CRUDController.findAll( Card(), supportFragmentManager , this, this)
        }
        else {
            buildScreenWithParameterCard( cardToAddExpense as Card )
            cardSelected = cardToAddExpense
            day          = cardToAddExpense.dueDate.toString()
        }

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

    private fun buildScreenWithParameterCard( card: Card ) {
        txt_card_to_expense.visibility          = View.VISIBLE
        list_day.visibility                     = View.GONE
        list_bank_for_add_expense.visibility    = View.GONE

        txt_card_to_expense.text                = card.name
    }

    private fun buildScreenWithoutParameterCard() {
        txt_card_to_expense.visibility          = View.GONE
        list_day.visibility                     = View.VISIBLE
        list_bank_for_add_expense.visibility    = View.VISIBLE
    }

    private fun saveExpense() {
        val expense     = Expense()
        expense.card    = cardSelected
        expense.dueDate = day.toInt()
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
