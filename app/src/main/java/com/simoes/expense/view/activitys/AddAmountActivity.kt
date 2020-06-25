package com.simoes.expense.view.activitys

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.model.models.Bank
import kotlinx.android.synthetic.main.activity_add_amount.*


class AddAmountActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var listBanks      : ArrayList<Bank>
    private lateinit var bankSelected   : Bank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_amount)

        CRUDController.findAll( Bank(), supportFragmentManager , this, this)

        btn_add_amount_bank.setOnClickListener {
            addAmountBank()
        }

        list_bank_for_add_amount.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView          : AdapterView<*>?,
                selectedItemView    : View?,
                position            : Int,
                id                  : Long
            ) {
                bankSelected = listBanks[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                //TODO implements
            }
        }
    }

    private fun inflateListBank( listBank : ArrayList<String> ) {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            listBank
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

        val edtAmount   = edt_amount_add.text
        val bank        = listBanks[0]
    }


    override fun callback(list: ArrayList<Any>){
        listBanks           = list as ArrayList<Bank>
        val listBankName    = getListBankName( listBanks )
        inflateListBank(listBankName)
    }
}
