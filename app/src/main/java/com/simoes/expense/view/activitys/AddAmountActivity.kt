package com.simoes.expense.view.activitys

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.model.models.Card
import kotlinx.android.synthetic.main.activity_add_amount.*

class AddAmountActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var listCards      : ArrayList<Card>
    private lateinit var cardSelected   : Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_amount)

        CRUDController.findAll( Card(), supportFragmentManager , this, this)

        btn_add_amount_bank.setOnClickListener {
            txt_add_amount.visibility       = View.GONE
            loading_add_amount.visibility   = View.VISIBLE
            btn_add_amount_bank.isEnabled   = true

            addAmountBank()
            clearScreen()
        }

        list_bank_for_add_amount.onItemSelectedListener = object : OnItemSelectedListener {
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

    private fun clearScreen() {
        edt_amount_add.text?.clear()
    }

    private fun inflateListBank( listBank : ArrayList<String> ) {
        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listBank
        )

        list_bank_for_add_amount.adapter = adapter
    }

    private fun getListBankName(listCard: ArrayList<Card> ) : ArrayList<String> {
        val names = ArrayList<String>()

        for ( bank in listCard ) {
            names.add( bank.name )
        }

        return names
    }

    private fun addAmountBank() {
        val edtAmount   =  edt_amount_add.text.toString().toDouble()
        val bank        = cardSelected
        bank.balance    += edtAmount

        CRUDController.update( bank, supportFragmentManager, this , this)
    }

    override fun callback(isSuccess: Boolean) {
        if ( isSuccess ) {
            txt_add_amount.visibility       = View.VISIBLE
            loading_add_amount.visibility   = View.GONE
            btn_add_amount_bank.isEnabled   = true
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show()
        }
    }

    override fun callback(list: ArrayList<Any>){
        if ( !list.isNullOrEmpty() ) {
            listCards           = list as ArrayList<Card>
            val listBankName    = getListBankName( listCards )
            inflateListBank(listBankName)
        }
    }
}
