package com.simoes.expense.view.activitys

import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.model.models.Card
import com.simoes.expense.view.adapters.CardAdapter
import kotlinx.android.synthetic.main.activity_list_card.*
import kotlinx.android.synthetic.main.fragment_expense.*
import java.util.ArrayList

class ListCardActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var listCard: ArrayList<Card>
    
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_list_card)

        findCards()

        swiperefreshCards.setOnRefreshListener {
            findCards()
        }
    }

    private fun findCards() {
        CRUDController.findAll( Card(), supportFragmentManager, this, this )
    }

    private fun configListViewCards() {
        list_cards.adapter = CardAdapter( this.listCard, this )
    }

    override fun callback(list: ArrayList<Any>) {
        this.listCard = list as ArrayList<Card>
        configListViewCards()

        if ( swiperefresh != null && swiperefresh.isRefreshing ){
            swiperefreshCards.isRefreshing = false
        }

    }
}