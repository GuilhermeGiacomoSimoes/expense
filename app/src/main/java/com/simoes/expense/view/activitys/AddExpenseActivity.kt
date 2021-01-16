package com.simoes.expense.view.activitys

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.helpers.TypeExpense
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.activity_add_expense.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class AddExpenseActivity : AppCompatActivity(), CallBackReturn {

    private var choiseCardOrMoney   = arrayOf("cartão", "dinheiro")
    private var day                 = "0"

    private lateinit var days           : Array<String>
    private lateinit var listCards      : ArrayList<Card>
    private lateinit var cardSelected   : Card
    private lateinit var typeExpense    : TypeExpense

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val cardToAddExpense = intent.getSerializableExtra( Helper.UUIDCARD )

        if ( cardToAddExpense == null ) {
            buildScreenWithoutParameterCard()
        }
        else {
            buildScreenWithParameterCard( cardToAddExpense as Card )
            cardSelected = cardToAddExpense
            day          = cardToAddExpense.dueDate.toString()
            typeExpense  = TypeExpense.CARD
        }

        btn_save_bank.setOnClickListener {
            val expense = createExpense()

            if ( expense.typeExpense == TypeExpense.MONEY ){
                expense.card = null
                saveExpense( expense )
            }
            else {
                val now         = Helper.dateNow()
                val monthNow    = now.split(" ")[0].split("/")[1]
                val yearNow     = now.split(" ")[0].split("/")[0]

                val dateExpCardStr = "$yearNow/$monthNow/${expense.card!!.dueDate}}"

                expense.dueDate = dateExpCardStr

                saveExpense( expense )
                updateCard ( expense )
            }
        }
    }

    private fun configListCardOrMoney(){
        if ( choise_card_or_money != null ){
            val adapter : ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                choiseCardOrMoney
            )

            choise_card_or_money.adapter = adapter
        }

        choise_card_or_money.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView          : AdapterView<*>?,
                selectedItemView    : View?,
                position            : Int,
                id                  : Long
            ) {
                typeExpense = if (choiseCardOrMoney[position] == "cartão") {
                    mountCardSelectionScreen()
                    TypeExpense.CARD
                } else {
                    screen_view.visibility = View.VISIBLE
                    mountMoneySelectionScreen()
                    TypeExpense.MONEY
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                //TODO implements
            }
        }
    }

    private fun mountCardSelectionScreen() {
        list_day.visibility                     = View.GONE
        list_bank_for_add_expense.visibility    = View.VISIBLE

        configListCards()
    }

    private fun mountMoneySelectionScreen() {
        list_day.visibility                     = View.VISIBLE
        list_bank_for_add_expense.visibility    = View.GONE

        configListDays()
    }

    private fun configListDays() {
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
    }

    private fun configListCards(){

        CRUDController.findAll( Card(), supportFragmentManager , this, this)

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
    }

    private fun buildScreenWithParameterCard( card: Card ) {
        screen_view.visibility                  = View.VISIBLE
        choise_card_or_money.visibility         = View.GONE
        txt_card_to_expense.visibility          = View.VISIBLE
        list_day.visibility                     = View.GONE
        list_bank_for_add_expense.visibility    = View.GONE
        choise_card_or_money.visibility         = View.GONE

        txt_card_to_expense.text                = card.name
    }

    private fun buildScreenWithoutParameterCard() {
        txt_card_to_expense.visibility          = View.GONE
        list_day.visibility                     = View.VISIBLE
        list_bank_for_add_expense.visibility    = View.VISIBLE
        choise_card_or_money.visibility         = View.VISIBLE

        configListCardOrMoney()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createExpense() : Expense {
        val expense  = Expense()

        expense.card = if ( typeExpense == TypeExpense.CARD ) {
            cardSelected
        } else {
            null
        }

        val now         = Helper.dateNow()
        val monthNow    = now.split(" ")[0].split("/")[1]
        val yearNow     = now.split(" ")[0].split("/")[0]

        val dateExpCardStr = "$yearNow/$monthNow/$day}"

        expense.dueDate = dateExpCardStr
        expense.name        = edt_expense_name  .text.toString()
        expense.value       = edt_amount_expense.text.toString().toDouble()
        expense.repeat      = chk_repeat.isChecked
        expense.typeExpense = typeExpense
        expense.date        = Helper.dateNow()

        return expense
    }

    private fun updateCard( expense: Expense ) {
        expense.card = null
        cardSelected.expenses.add( expense )
        CRUDController.update( cardSelected, supportFragmentManager, this     )
    }

    private fun saveExpense( expense: Expense ){
        CRUDController.create(expense, supportFragmentManager, this)
    }

    private fun inflateListBank( listBank : ArrayList<String> ) {
        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
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
        if (!list.isNullOrEmpty()){
            listCards           = list as ArrayList<Card>
            val listBankName    = getListBankName( listCards )
            inflateListBank(listBankName)

            screen_view.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent( this, MainActivity ::class.java )
        startActivity(intent)
    }
}
