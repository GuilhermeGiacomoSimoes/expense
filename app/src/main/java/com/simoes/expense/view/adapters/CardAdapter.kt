package com.simoes.expense.view.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.simoes.expense.R
import com.simoes.expense.model.models.Card
import java.util.ArrayList

class CardAdapter( private var listCard: ArrayList<Card>, private var context: Context ) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val card = listCard[position]
        val layout : View

        layout = if ( convertView == null ) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.card_adapter, null)
        } else {
            convertView
        }

        val progresBarCard      = layout.findViewById<TextView>(R.id.progres_bar_card)




        var progressBarAnimator : ObjectAnimator =
            ObjectAnimator.ofInt( progresBarCard, "", 0, 1000 )



        return layout

    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }


}