package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.FlagCards
import com.simoes.expense.model.models.Card
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : AppCompatActivity() {

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
            createCard()
        }
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


    private fun saveBank(card: Card) {
        CRUDController.create( card, this.supportFragmentManager , this)
    }

}
