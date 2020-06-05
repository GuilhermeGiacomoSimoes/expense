package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.model.models.Bank
import kotlinx.android.synthetic.main.activity_add_bank.*
import java.util.*

class AddBankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bank)

        btn_save_bank.setOnClickListener {
            createBank()
        }

    }

    private fun createBank() {
        val bank = Bank()

        bank.name    = edt_name_bank.text.toString()
        bank.balance = edt_amount_bank.text.toString().toDouble()
        bank.uuid    = UUID.randomUUID().toString()

        saveBank( bank )
    }


    private fun saveBank(bank: Bank) {
        CRUDController.create( bank, this.supportFragmentManager )
    }

}
