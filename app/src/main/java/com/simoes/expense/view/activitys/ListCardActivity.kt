package com.simoes.expense.view.activitys

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.helpers.NameClasses
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Wallet
import com.simoes.expense.view.adapters.CardAdapter
import kotlinx.android.synthetic.main.activity_list_card.*
import java.util.ArrayList

class ListCardActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var listCard: ArrayList<Card>
    private var amountWallet = .0
    private var requisitionCounter = 0
    private var hideBalanceCardsAndWallet = false

    override fun onResume() {
        super.onResume()

        setContentView(R.layout.activity_list_card)

        showOrHideSpinner( !swiperefreshCards.isRefreshing )

        configShowOrViewBalances()

        findCards()
        findWallet()

        swiperefreshCards.setOnRefreshListener {
            layout_amount_wallet_and_cards.visibility = View.GONE
            findCards()
            findWallet()
        }

        hide_balance_cards_and_wallet.setOnClickListener {
            hideBalance( ! hideBalanceCardsAndWallet )
        }
    }

    private fun configShowOrViewBalances() {
        val r = Helper.getPersistData(Helper.PERSIST_VIEW_BALANCE_LIST_CARDS, this)

        if (r != null) {
            hideBalanceCardsAndWallet = ( r ==  true.toString() )
            hideBalance( r ==  true.toString() )
        }
    }

    private fun hideBalance( hide : Boolean ) {
        if ( hide ) {
            hide_balance_cards_and_wallet.setImageResource(R.drawable.ic_eyes)

            view_hide_balance_wallet.visibility = View.GONE
            edt_balance_waller.visibility = View.VISIBLE

            view_hide_balance_cards.visibility = View.GONE
            edt_balance_card.visibility = View.VISIBLE
        }
        else {
            hide_balance_cards_and_wallet.setImageResource(R.drawable.ic_eyes_cancel)

            view_hide_balance_wallet.visibility = View.VISIBLE
            edt_balance_waller.visibility = View.GONE

            view_hide_balance_cards.visibility = View.VISIBLE
            edt_balance_card.visibility = View.GONE
        }

        Helper.persistData(Helper.PERSIST_VIEW_BALANCE_LIST_CARDS, this, hide.toString())
    }

    private fun findWallet() {
        CRUDController.findAll( Wallet(), supportFragmentManager, this, this )
    }

    private fun findCards() {
        CRUDController.findAll( Card(), supportFragmentManager, this, this )
    }

    private fun configListViewCards() {
        val cards = ArrayList<Card>()
        cards.add(Card())
        cards.addAll(this.listCard)

        list_cards.adapter = CardAdapter( cards, this )
    }

    private fun showOrHideSpinner( show : Boolean ) {
        if ( swiperefreshCards != null ){
            swiperefreshCards.isRefreshing = show
        }
    }

    fun reload() {
        finish()
        startActivity( intent )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ( resultCode == Activity.RESULT_OK ) {
            if ( requestCode == Helper.EXPENSE_PAY_INVOICE ) {
                finish()
                startActivity(Intent(this, ListCardActivity ::class.java))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun configAmountCards( cards : ArrayList<Card> ) {
        var amountCard = .0

        for ( card in cards ) {
            amountCard += card.balance
        }

        edt_balance_card.text = Helper.getValueMoney(amountCard).split("$")[1].trim()
    }

    private fun configAmountWallet( wallet : Wallet ) {
        edt_balance_waller.text = Helper.getValueMoney(wallet.amount).split("$")[1].trim()
    }

    override fun callback(isSuccess: Boolean) {
        return
    }

    override fun callback(list: ArrayList<Any>) {
        requisitionCounter ++

        if( ! list.isNullOrEmpty()) {
            if ( list[0].javaClass.name == "com.simoes.expense.model.models.${NameClasses.Card.name}" ) {
                this.listCard = list as ArrayList<Card>
                configListViewCards()
                configAmountCards(this.listCard)
            } else {
                val wallet = list[0] as Wallet
                this.amountWallet = wallet.amount
                configAmountWallet(wallet)
            }
        }
        else {
            txt_not_cards.visibility = View.VISIBLE
        }

        if (requisitionCounter > 1){
            layout_amount_wallet_and_cards.visibility = View.VISIBLE
            requisitionCounter = 0
            showOrHideSpinner( ! swiperefreshCards.isRefreshing )
        }
    }

    override fun onBackPressed() {
        startActivity( Intent(this, MainActivity ::class.java) )
    }
}