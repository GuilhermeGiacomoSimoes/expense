package com.simoes.expense.view.activitys

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.model.models.Bank
import kotlinx.android.synthetic.main.activity_add_amount.*
import java.util.*

class AddAmountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_amount)

        val listBank = CRUDController.findAll( Bank(), supportFragmentManager ) as ArrayList<Bank>

        if (!listBank.isNullOrEmpty()){
            val listBankName  = getListBankName     ( listBank )
            inflateListBank                         ( listBankName )
        }

    }

    private fun inflateListBank( listBank : ArrayList<String> ) {

        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, listBank
        )

        list_bank_for_add_amount.adapter = adapter
    }

    private fun getListBankName( listBank: ArrayList<Bank> ) : ArrayList<String> {

        val names = ArrayList<String>()

        for ( bank in listBank ) {
            names.add( bank.name )
        }

        return names
    }

}
