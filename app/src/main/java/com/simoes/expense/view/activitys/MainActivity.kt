package com.simoes.expense.view.activitys

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_expenses.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
        btn_history.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
        btn_more.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_click_item)
        txt_expense_view.visibility = View.GONE
        txt_graph_view.visibility = View.GONE
        txt_more_view.visibility = View.VISIBLE

        btn_expenses.setOnClickListener {
            btn_expenses.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_click_item)
            btn_history.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
            btn_more.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
            txt_expense_view.visibility = View.VISIBLE
            txt_graph_view.visibility = View.GONE
            txt_more_view.visibility = View.GONE

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ExpenseFragment())
            ft.commit()
        }

        btn_history.setOnClickListener {
            btn_expenses.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
            btn_history.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_click_item)
            btn_more.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
            txt_expense_view.visibility = View.GONE
            txt_graph_view.visibility = View.VISIBLE
            txt_more_view.visibility = View.GONE

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, ChartsFragment())
            ft.commit()
        }

        btn_more.setOnClickListener {
            btn_expenses.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
            btn_history.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_dont_click_item)
            btn_more.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.color_click_item)
            txt_expense_view.visibility = View.GONE
            txt_graph_view.visibility = View.GONE
            txt_more_view.visibility = View.VISIBLE

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.container, MoreFragment())
            ft.commit()
        }

        verifyWallet()
    }

    private fun verifyWallet() {
        val walletCreated = Helper.getPersistData(Helper.WALLET_CREATED, this)
        if ( walletCreated == null || walletCreated == false.toString()) {
            CRUDController.findAll(Wallet(), supportFragmentManager, this, this)
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
