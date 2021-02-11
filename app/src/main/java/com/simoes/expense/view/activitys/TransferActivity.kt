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
import kotlinx.android.synthetic.main.activity_transfer.*
import java.util.ArrayList

class TransferActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var cardDe     : Card
    private lateinit var cardPara   : Card
    private lateinit var cards      : ArrayList<Card>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        findCards()
        buildClickEventSpinners()
    }

    private fun buildClickEventSpinners() {
        spn_de.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cardDe = cards[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spn_para.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cardPara = cards[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun findCards() {
        CRUDController.findAll(Card(), supportFragmentManager, this, this)
    }

    private fun buildSpinner( list: ArrayList<Card> ){
        val cardsNames = getCardsNames( list )

        val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item, cardsNames )

        spn_de.adapter = adapter
        spn_para.adapter = adapter
    }

    private fun getCardsNames( list : ArrayList<Card>) : ArrayList<String> {
        val names = ArrayList<String>()

        for ( card in list ) {
            names.add(card.name)
        }

        return names
    }

    override fun callback(list: ArrayList<Any>) {
        if (list.isNotEmpty()) {
            buildSpinner(list as ArrayList<Card>)
        }
    }

    override fun callback(isSuccess: Boolean) {
        TODO("Not yet implemented")
    }

}