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
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import com.simoes.expense.view.adapters.ExpenseAdapter
import kotlinx.android.synthetic.main.fragment_expense.*
import java.util.ArrayList

class ExpenseFragment : Fragment(), CallBackReturn {

    private lateinit var listExpense        : ArrayList<Expense>
    private lateinit var listCards          : ArrayList<Card>
    private          var hideBalance        = false
    private          var sumBalance         = .0

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
            findInformations()
        }

        hide_balance.setOnClickListener {
            hideBalance()
        }

        swiperefresh.setOnRefreshListener {
            findInformations()
        }
    }

    private fun findInformations() {
        swiperefresh.isRefreshing = true

        list_expenses.invalidate ( )
        CRUDController.findAll  ( Card(),     fragmentManager!!, this, context!! )
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

    private fun getListBankBalance(listCard : ArrayList<Card> ) : ArrayList<Double> {
        val listBalance = ArrayList<Double>()

        for ( bank in listCard ) {
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

    private fun configListViewExpense() {
        if ( context != null ) {
            list_expenses.adapter = ExpenseAdapter( listExpense , context!!)
        }
    }

    private fun getAllExpenses( listCards : ArrayList<Card>) : ArrayList<Expense> {
        val expenses = ArrayList<Expense>()

        for ( card in listCards ) {
            expenses.addAll( card.expenses )
        }

        return expenses
    }

    override fun callback(list: ArrayList<Any>) {
        if ( list[0].javaClass.name == "com.simoes.expense.model.models.${NameClasses.Card.name}" ){
            listCards               = list as ArrayList<Card>

            val listBankBalance     = getListBankBalance ( listCards )
            val sumOfBalances       = sumOfBalances      ( listBankBalance )
            this.listExpense        = getAllExpenses     ( listCards )

            changeBalanceView       ( sumOfBalances )
            configListViewExpense   ( )
            this.sumBalance = sumOfBalances
        }

        if ( swiperefresh != null && swiperefresh.isRefreshing ){
            swiperefresh.isRefreshing = false
        }

    }
}
