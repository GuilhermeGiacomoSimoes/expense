package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.FlagCards
import com.simoes.expense.model.models.Card
import com.simoes.expense.view.fragments.FeedbackDialog
import kotlinx.android.synthetic.main.activity_add_card.*
import java.util.ArrayList

class AddCardActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var days           : Array<String>
    private          var day            = 0
    private          val listFlags      = arrayOf( FlagCards.VISA.name, FlagCards.MASTERCARD.name )
    private lateinit var flagSelected   : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        inflateListDays ()
        inflateListFlags()

        configListDays ()
        configListFlags()

        btn_save_card.setOnClickListener {
            if (checkIfTheMandatoryFieldsAreFilled()) {
                txt_btn_save_card.visibility    = View.GONE
                loading_add_card.visibility     = View.VISIBLE
                btn_save_card.isEnabled         = false
                createCard()

                clearScreen()
            } else {
                FeedbackDialog.showDialog(supportFragmentManager, "Favor preencher campos obrigatórios", "Campos obrigatórios em branco")
            }
        }
    }

    private fun checkIfTheMandatoryFieldsAreFilled() : Boolean {
        return edt_name_card.text?.isNotEmpty() == true && edt_limit_card.text?.isNotEmpty() == true && edt_balance_card.text?.isNotEmpty() == true
    }

    private fun clearScreen() {
        edt_name_card.text?.clear()
        edt_limit_card.text?.clear()
        edt_balance_card.text?.clear()
    }

    private fun configListDays () {
        if ( due_day != null ) {
            due_day.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView          : AdapterView<*>?,
                    selectedItemView    : View?,
                    position            : Int,
                    id                  : Long
                ) {
                    day = Integer.parseInt( days[ position ] )
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    //TODO implements
                }
            }
        }
    }

    private fun configListFlags () {
        if ( flag_card != null ) {
            flag_card.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView          : AdapterView<*>?,
                    selectedItemView    : View?,
                    position            : Int,
                    id                  : Long
                ) {
                    flagSelected = listFlags[position]
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    //TODO implements
                }
            }
        }
    }

    private fun inflateListFlags( ) {
        if ( flag_card != null ){
            val adapter : ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listFlags
            )

            flag_card.adapter = adapter
        }
    }

    private fun inflateListDays( ){
        if ( due_day != null ) {
            days =  resources.getStringArray( R.array.days )

            ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item)
                .also {adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    due_day.adapter = adapter
                }
        }
    }

    private fun createCard() {
        val card = Card()

        if ( this.flagSelected == FlagCards.MASTERCARD.name ){
            card.flagCards = FlagCards.MASTERCARD
        }
        else if ( this.flagSelected == FlagCards.VISA.name ){
            card.flagCards = FlagCards.VISA
        }

        card.name       = edt_name_card.text.toString()
        card.limit      = edt_limit_card.text.toString().toDouble()
        card.dueDate    = day
        card.limit      = edt_limit_card.text.toString().toDouble()
        card.balance    = edt_balance_card.text.toString().toDouble()

        saveBank( card )
    }

    override fun callback(list: ArrayList<Any>) {
        TODO("Not yet implemented")
    }

    override fun callback(isSuccess: Boolean) {
        if ( isSuccess ) {
            txt_btn_save_card.visibility    = View.VISIBLE
            loading_add_card.visibility     = View.GONE
            btn_save_card.isEnabled         = true
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveBank(card: Card) {
        CRUDController.create( card, this.supportFragmentManager , this,  this)
    }

}
