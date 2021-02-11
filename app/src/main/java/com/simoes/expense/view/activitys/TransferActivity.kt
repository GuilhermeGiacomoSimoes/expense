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
import com.simoes.expense.model.models.Card
import com.simoes.expense.view.fragments.FeedbackDialog
import kotlinx.android.synthetic.main.activity_transfer.*
import java.util.ArrayList

class TransferActivity : AppCompatActivity(), CallBackReturn {

    private var cardDe     = Card()
    private var cardPara   = Card()
    private var cards      = ArrayList<Card>()
    private var countCallback = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        findCards()
        buildClickEvents()
    }

    private fun verifyValue() = edt_value_transfer.text.toString().isEmpty()

    private fun buildClickEvents() {

        btn_transfer.setOnClickListener {
            loading_transfer.visibility = View.VISIBLE
            txt_btn_transfer.visibility = View.GONE

            if ( cardDe != cardPara ){
                if ( verifyValue() ) {
                    loading_transfer.visibility = View.GONE
                    txt_btn_transfer.visibility = View.VISIBLE
                    FeedbackDialog.showDialog(supportFragmentManager, "Preencha o campo de valor", "Erro")
                } else{
                    val value = edt_value_transfer.text.toString().toDouble()
                    if ( cardDe.balance < value) {
                        loading_transfer.visibility = View.GONE
                        txt_btn_transfer.visibility = View.VISIBLE
                        FeedbackDialog.showDialog(supportFragmentManager, "O saldo do banco de origem não é o suficiente", "Erro")
                    } else {
                        cardDe.balance -= value
                        cardPara.balance += value

                        CRUDController.update( cardDe, supportFragmentManager, this, this )
                        CRUDController.update( cardPara, supportFragmentManager, this, this )
                    }
                }
            } else {
                loading_transfer.visibility = View.GONE
                txt_btn_transfer.visibility = View.VISIBLE
                FeedbackDialog.showDialog(supportFragmentManager, "O banco de origem não pode ser igualao banco de destino", "Erro")
            }
        }

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
                return
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
            this.cards = list as ArrayList<Card>
            buildSpinner(list as ArrayList<Card>)
        }
    }

    override fun callback(isSuccess: Boolean) {
        if( isSuccess ) {
            countCallback ++
        }

        if ( countCallback > 1 ) {
            loading_transfer.visibility = View.GONE
            txt_btn_transfer.visibility = View.VISIBLE
            Toast.makeText(this, "Transferência realizada com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

}