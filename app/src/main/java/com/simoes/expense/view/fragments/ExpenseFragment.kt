package com.simoes.expense.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.helpers.NameClasses
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import com.simoes.expense.model.models.Wallet
import com.simoes.expense.view.adapters.ExpenseAdapter
import kotlinx.android.synthetic.main.expenses_adapter.*
import kotlinx.android.synthetic.main.fragment_expense.*


class ExpenseFragment : Fragment(), CallBackReturn {

    private          var wallet             : Wallet? = null
    private lateinit var listExpense        : ArrayList<Expense>
    private lateinit var listCards          : ArrayList<Card>
    private          var hideBalance        = false
    private          var breakCount         = 0
    private          var expensesDoNotExist = true
    private          var reloadScreen       = false
    private          var iRequestData       = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense , container, false)
    }

    override fun onResume() {
        super.onResume()

        edt_balance.text = "0,00"

        if ( fragmentManager != null && context != null ) {
            iRequestData = true
            findInformations()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraSwipeAndDelete()

        if(context != null) {
            hideBalance = Helper.getPersistData(Helper.PERSIST_VIEW_BALANCE, context!!).equals(true.toString())
        }
        
        hide_balance.setOnClickListener {
            hideBalance  = ! hideBalance
            hideBalance()
        }

        swiperefresh.setOnRefreshListener {
            layout_balance.visibility = View.GONE
            edt_balance.text = "0,00"
            iRequestData = true
            findInformations()
        }
    }

    private fun configuraSwipeAndDelete() {
//        val touchListener = SwipeToDismissTouchListener(
//            ListViewAdapter(list_expenses),
//            object : DismissCallbacks<ListViewAdapter?> {
//                override fun canDismiss(position: Int): Boolean {
//                    return true
//                }
//
//                override fun onDismiss(view: ListViewAdapter?, position: Int) {
//                    //
//                }
//            })
//
//        list_expenses.setOnTouchListener(touchListener)
    }

    private fun findInformations() {
        swiperefresh.isRefreshing = true

        list_expenses.invalidate ( )
        CRUDController.findAll  ( Card(),       fragmentManager!!, this, context!! )
        CRUDController.findAll  ( Expense(),    fragmentManager!!, this, context!! )
        CRUDController.findAll  ( Wallet(),     fragmentManager!!, this, context!! )
    }

    private fun hideBalance() {
        if ( hideBalance ) {
            if ( context != null ){
                hide_balance.setImageDrawable( ContextCompat.getDrawable( context!!, R.drawable.ic_eyes ) )
            }

            view_hide_balance.visibility = View.GONE
            edt_balance      .visibility = View.VISIBLE

        } else {
            if ( context != null ){
                hide_balance.setImageDrawable( ContextCompat.getDrawable( context!!, R.drawable.ic_eyes_cancel ) )
            }

            view_hide_balance.visibility = View.VISIBLE
            edt_balance      .visibility = View.GONE
        }

        Helper.persistData( Helper.PERSIST_VIEW_BALANCE, context!!, hideBalance.toString() )
    }

    private fun getListBankBalance(listCard : ArrayList<Card> ) : ArrayList<Double> {
        val listBalance = ArrayList<Double>()

        for ( bank in listCard ) {
            listBalance.add( bank.balance )
        }

        return listBalance
    }

    private fun changeBalanceView( amount : Double ) {
        if (context != null) {
            if ( edt_balance != null){

                var valueNow = Helper.moneyStrToDouble(edt_balance.text.toString())
                valueNow += amount

                val value = Helper.getValueMoney(valueNow)
                edt_balance.text = value.split("$")[1].trim()
                hideBalance()
            }
        }
    }

    private fun sumOfBalances( listBalance: ArrayList<Double> ) : Double {
        var summation = .0

        for ( balance in listBalance ){
            summation += balance
        }

        return summation
    }

    private fun sortExpense( listExpense : ArrayList<Expense>) : ArrayList<Expense> {
        val arrayReturn = arrayListOf<Expense>()
        arrayReturn.addAll(listExpense)

        for (expense in listExpense) {
            if ( expense.paidOut ) {
                arrayReturn.remove(expense)
                arrayReturn.add(expense)
            }
            else if (Helper.expenseOwn(expense)) {
                arrayReturn.remove(expense)
                arrayReturn.add(0,expense)
            }
        }

        return arrayReturn
    }

    private fun configListViewExpense() {
        if ( context != null ) {
            listExpense = sortExpense( listExpense )
            listExpense.add(Expense())

            val aux = ArrayList<Expense>()
            aux.addAll(listExpense)
            listExpense.clear()

            listExpense.add( Expense() )
            listExpense.addAll(aux)

            list_expenses.adapter = ExpenseAdapter( listExpense , context!!, fragmentManager!!, this , this.wallet)
            list_expenses.layoutManager = LinearLayoutManager( context )

            val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
                ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT
                ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    Toast.makeText(context, "on Swiped ", Toast.LENGTH_SHORT).show()
                    //Remove swiped item from list and notify the RecyclerView
//                    val position = viewHolder.adapterPosition
//                    arrayList.remove(position)
//                    adapter.notifyDataSetChanged()
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val top = itemView.top + (itemView.height - 70) / 2
                    val left = itemView.width - ( DisplayMetrics().widthPixels / resources.displayMetrics.scaledDensity ) - (itemView.height - 70) / 2
                    val right = left + 70
                    val bottom = top + 70

                    val icon = ContextCompat.getDrawable( context!!, R.drawable.delete )
                    val background = ColorDrawable(Color.RED)

                    if (dX < 0) {
                        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                        icon!!.setBounds(left.toInt(), top, right.toInt(), bottom)
                    }

                    background.draw(c)
                    icon!!.draw(c)

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(list_expenses)
        }
    }

    private fun getCardByExpense( expense: Expense ) : Card ? {
        for (card in listCards) {
            for (e in card.expenses) {
                if ( e.uuid == expense.uuid) {
                    e.paidOut = true

                    return card
                }
            }
        }

        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        reloadScreen = true

        if (resultCode == Activity.RESULT_OK) {
            if  (requestCode == Helper.PAYMENT_EXPENSE) {
                val expense = data?.getSerializableExtra(Helper.EXPENSE_RETURN) as Expense

                if ( expense.card != null ) {
                    val card = getCardByExpense( expense )

                    if (fragmentManager != null && context != null){
                        card!!.balance -= expense.value
                        CRUDController.update(card!!, fragmentManager!!, context!!, this)
                    }
                }
                else {
                    if (wallet != null) {
                        wallet!!.amount -= expense.value
                        CRUDController.update(wallet!!, fragmentManager!!, context!!, this)
                    }
                }
            }

            if (requestCode == Helper.DELETE_EXPENSE) {
                val expense = data?.getSerializableExtra(Helper.EXPENSE_RETURN) as Expense

                if (expense.card != null) {
                    val card = expense.card!!
                    card.expenses.remove( expense )

                    if (fragmentManager != null && context != null) {
                        CRUDController.update(card, fragmentManager!!, context!!, this)
                    }
                }
            }
        }
    }

    override fun callback(list: ArrayList<Any>) {
        if (iRequestData){

            breakCount ++;

            if (!list.isNullOrEmpty()){
                when (list[0].javaClass.name) {
                    "com.simoes.expense.model.models.${NameClasses.Card.name}" -> {
                        listCards               = list as ArrayList<Card>

                        val listCardBalance     = getListBankBalance ( listCards )
                        val balance            =  sumOfBalances      ( listCardBalance )
                        changeBalanceView( balance )
                    }
                    "com.simoes.expense.model.models.${NameClasses.Expense.name}" -> {
                        this.listExpense        = list as ArrayList<Expense>
                        configListViewExpense   ( )
                        expensesDoNotExist      = false
                    }
                    "com.simoes.expense.model.models.${NameClasses.Wallet.name}" -> {
                        this.wallet = list[0] as Wallet
                        var balance = .0

                        if (this.wallet != null) {
                            balance += this.wallet!!.amount
                        }

                        changeBalanceView( balance )
                    }
                }
            }

            if (breakCount >= 3) {
                layout_balance.visibility = View.VISIBLE

                iRequestData = false

                if (expensesDoNotExist) {
                    expensesDoNotExist = true
                    txt_not_expenses.visibility = View.VISIBLE
                }

                breakCount = 0
                if ( swiperefresh != null && swiperefresh.isRefreshing ) {
                    swiperefresh.isRefreshing = false
                }
            }
        }
    }

    override fun callback(isSuccess: Boolean) {
        if (reloadScreen){
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }
    }
}