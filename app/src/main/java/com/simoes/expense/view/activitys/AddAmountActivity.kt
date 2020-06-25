package com.simoes.expense.view.activitys

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.model.models.Bank
import kotlinx.android.synthetic.main.activity_add_amount.*
import java.util.*

class AddAmountActivity : AppCompatActivity(), CallBackReturn {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_amount)

        CRUDController.findAll( Bank(), supportFragmentManager , this, this)

        btn_add_amount_bank.setOnClickListener {
            addAmountBank()
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


    private fun addAmountBank() {

        val edtAmount = edt_amount_add.text
        val bank      =

    }


    override fun callback(list: ArrayList<Any>){
        val listBankName = getListBankName( list as ArrayList<Bank> )
        inflateListBank(listBankName)
    }
}
