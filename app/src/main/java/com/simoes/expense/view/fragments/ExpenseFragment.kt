package com.simoes.expense.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.NameClasses
import com.simoes.expense.model.models.Bank
import com.simoes.expense.model.models.Expense
import kotlinx.android.synthetic.main.fragment_expense.*
import java.util.ArrayList

class ExpenseFragment : Fragment(), CallBackReturn {

    private lateinit var listBanks  : ArrayList<Bank>
    private          var hideBalance    = false
    private          var sumBalance     = .0

    private lateinit var listExpense : ArrayList<Expense>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense , container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ( fragmentManager != null && context != null ) {
            CRUDController.findAll( Bank(),     fragmentManager!!, this, context!! )
            CRUDController.findAll( Expense(),  fragmentManager!!, this, context!! )
        }

        hide_balance.setOnClickListener {
            hideBalance()
        }
    }

    private fun hideBalance() {
        if ( hideBalance ) {
            if ( context != null ){
                hide_balance.setImageDrawable( ContextCompat.getDrawable( context!!, R.drawable.ic_eyes ) )
            }

            view_hide_balance.visibility = View.GONE
            edt_balance      .visibility = View.VISIBLE

            hideBalance = !hideBalance

        } else {
            if ( context != null ){
                hide_balance.setImageDrawable( ContextCompat.getDrawable( context!!, R.drawable.ic_eyes_cancel ) )
            }

            view_hide_balance.visibility = View.VISIBLE
            edt_balance      .visibility = View.GONE

            hideBalance = !hideBalance
        }
    }

    private fun getListBankBalance( listBank : ArrayList<Bank> ) : ArrayList<Double> {
        val listBalance = ArrayList<Double>()

        for ( bank in listBank ) {
            listBalance.add( bank.balance )
        }

        return listBalance
    }

    private fun changeBalanceView( balances: Double ) {
        if ( edt_balance != null){
            edt_balance.text = balances.toString()
        }
    }

    private fun sumOfBalances( listBalance: ArrayList<Double> ) : Double {
        var summation = .0

        for ( balance in listBalance ){
            summation += balance
        }

        return summation
    }

    private fun getListExpenseDescription( listExpense : ArrayList<Expense> ) : ArrayList<String> {
        val expenseDescription = ArrayList<String>()

        for ( expense in listExpense ) {
            expenseDescription.add( expense.toString() )
        }

        return expenseDescription
    }

    override fun callback(list: ArrayList<Any>) {
        if ( list[0].javaClass.name == NameClasses.Bank.name ){
            listBanks               = list as ArrayList<Bank>
            val listBankBalance     = getListBankBalance ( listBanks )
            val sumOfBalances       = sumOfBalances      ( listBankBalance )
            changeBalanceView( sumOfBalances )

            this.sumBalance = sumOfBalances
        }
        else if ( list[0].javaClass.name == NameClasses.Expense.name ) {
            listExpense = list as ArrayList<Expense>
            val listExpenseDescription = getListExpenseDescription( listExpense )
        }
    }

}
