package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Wallet
import com.simoes.expense.view.fragments.ChartsFragment
import com.simoes.expense.view.fragments.ExpenseFragment
import com.simoes.expense.view.fragments.MoreFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), CallBackReturn {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_expenses.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ExpenseFragment())
            ft.commit()
        }

        btn_history.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ChartsFragment())
            ft.commit()
        }

        btn_more.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, MoreFragment())
            ft.commit()
        }
    }

    override fun onResume() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, MoreFragment())
        ft.commit()
        super.onResume()
    }

    private fun createWallet() : Wallet {
        val wallet = Wallet()
        wallet.amount = .0

        return wallet
    }

    override fun callback(list: ArrayList<Any>) {
        if ( list.isNullOrEmpty() ) {
            val wallet = createWallet()
            CRUDController.create(wallet, supportFragmentManager, this, this)
        }
    }

    override fun callback(isSuccess: Boolean) {
        Helper.persistData(Helper.WALLET_CREATED, this, isSuccess.toString())
    }
}
