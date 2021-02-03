package com.simoes.expense.view.activitys

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.helpers.TypeCategory
import com.simoes.expense.helpers.TypeExpense
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import com.simoes.expense.view.adapters.CategoryAdapter
import com.simoes.expense.view.fragments.FeedbackDialog
import kotlinx.android.synthetic.main.activity_add_expense.*
import java.util.ArrayList

class AddExpenseActivity : AppCompatActivity(), CallBackReturn {

    private var choiseCardOrMoney   = arrayOf("cartão", "dinheiro")
    private var day                 = "0"

    private lateinit var days           : Array<String>
    private lateinit var listCards      : ArrayList<Card>
    private lateinit var cardSelected   : Card
    private lateinit var typeExpense    : TypeExpense
    private lateinit var typeCategory   : TypeCategory
    private var amount = 0.0

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

        configListCategory()

        btn_save_bank.setOnClickListener {
            if ( checkIfTheMandatoryFieldsAreFilled()) {
                if ( chk_paidout.isChecked && checkAmount()) {
                    txt_btn_save_expense.visibility     = View.GONE
                    loading_add_expense.visibility      = View.VISIBLE
                    btn_save_bank.isEnabled             = false

                    val expense = createExpense()
                    saveExpense( expense )

                    if ( expense.typeExpense == TypeExpense.CARD ){
                        expense.card = null
                        updateCard ( expense )
                    }

                    clearScreen()
                } else {
                    FeedbackDialog.showDialog( supportFragmentManager,"Valor da despesa maior do que o seu saldo", "ERRO" )
                }

            } else {
                FeedbackDialog.showDialog( supportFragmentManager,"Favor preencher campos obrigatórios", "Campos obrigatórios em branco" )
            }
        }
    }

    private fun checkAmount() = edt_amount_expense.text.toString().toDouble() > amount
    private fun checkIfTheMandatoryFieldsAreFilled() = edt_expense_name.text?.isNotEmpty() == true && edt_amount_expense.text?.isNotEmpty() == true

    private fun clearScreen() {
        edt_expense_name.text?.clear()
        edt_amount_expense .text?.clear()

        chk_repeat.isChecked    = false
        chk_paidout.isChecked   = false
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

    private fun configListCategory () {
        val arrayCaegory = arrayListOf(
            TypeCategory.PUB,
            TypeCategory.SUPERMARKET,
            TypeCategory.ALCOHOLIC_BEVERAGES,
            TypeCategory.TICKETS,
            TypeCategory.FOOD,
            TypeCategory.INTERNET,
            TypeCategory.WATER,
            TypeCategory.ENERGY,
            TypeCategory.FURNITURE,
            TypeCategory.OTHER
        )

        category_spinner.adapter = CategoryAdapter(this, arrayCaegory)

        category_spinner.onItemSelectedListener = object  :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                typeCategory = arrayCaegory[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
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
        val expense = Expense()

        expense.card = if ( typeExpense == TypeExpense.CARD ) {
            cardSelected
        } else {
            null
        }

        val now         = Helper.dateNow()
        var monthNow    = now.split(" ")[0].split("/")[1]
        var yearNow     = now.split(" ")[0].split("/")[0]
        val dayNow      = now.split(" ")[0].split("/")[2]

        val dayExp = if ( typeExpense == TypeExpense.CARD ) expense.card?.dueDate!!.toInt() else day.toInt()

        if ( dayExp < Integer.parseInt(dayNow)) {

            var monthInteger = Integer.parseInt(monthNow)
            var yearInteger  = Integer.parseInt(yearNow)

            if ( monthInteger < 11 ) {
                monthInteger ++
            }
            else {
                monthInteger = 1
                yearInteger ++
            }

            monthNow = if (monthInteger < 10) "0${monthInteger}" else monthInteger.toString()
            yearNow = yearInteger.toString()
        }

        val dayExpStr       = if ( dayExp < 10 ) "0${dayExp}" else dayExp.toString()
        val dateExpCardStr  = "${dayExpStr}/$monthNow/$yearNow"

        expense.dueDate = dateExpCardStr

        expense.dueDate     = dateExpCardStr
        expense.name        = edt_expense_name  .text.toString()
        expense.value       = edt_amount_expense.text.toString().toDouble()
        expense.repeat      = chk_repeat.isChecked
        expense.typeExpense = typeExpense
        expense.date        = Helper.dateNow()
        expense.category    = this.typeCategory
        expense.paidOut     = chk_paidout.isChecked

        return expense
    }

    private fun updateCard( expense: Expense ) {
        expense.card = null
        cardSelected.expenses.add( expense )
        CRUDController.update( cardSelected, supportFragmentManager, this , this)
    }

    private fun saveExpense( expense: Expense ){
        CRUDController.create(expense, supportFragmentManager, this, this)
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

            calcAmount( listCards )
        }
    }

    private fun calcAmount( cards: ArrayList<Card> ) {
        for ( card in cards ) {
            amount += card.balance
        }
    }

    override fun callback(isSuccess: Boolean) {
        if (isSuccess) {
            txt_btn_save_expense.visibility     = View.VISIBLE
            loading_add_expense.visibility      = View.GONE
            btn_save_bank.isEnabled             = true
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent( this, MainActivity ::class.java )
        startActivity(intent)
    }
}
