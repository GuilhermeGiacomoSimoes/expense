package com.simoes.expense.view.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Card
import com.simoes.expense.view.adapters.CardAdapter
import kotlinx.android.synthetic.main.activity_list_card.*
import java.util.ArrayList

class ListCardActivity : AppCompatActivity(), CallBackReturn {

    private lateinit var listCard: ArrayList<Card>

    override fun onResume() {
        super.onResume()

        setContentView(R.layout.activity_list_card)

        showOrHideSpinner( !swiperefreshCards.isRefreshing )

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

    override fun callback(isSuccess: Boolean) {
        TODO("Not yet implemented")
    }

    override fun callback(list: ArrayList<Any>) {
        if( ! list.isNullOrEmpty()){
            this.listCard = list as ArrayList<Card>
            configListViewCards()

            showOrHideSpinner( !swiperefreshCards.isRefreshing )
        }
    }

    override fun onBackPressed() {
        startActivity( Intent(this, MainActivity ::class.java) )
    }
}