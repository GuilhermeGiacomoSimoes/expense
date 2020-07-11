package com.simoes.expense.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.simoes.expense.R
import com.simoes.expense.helpers.FlagCards
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.models.Card
import com.simoes.expense.view.activitys.AddExpenseActivity
import java.text.SimpleDateFormat
import java.util.*

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

        val progresBarCard      = layout.findViewById<ProgressBar>(R.id.progres_bar_card)
        val txtDescription      = layout.findViewById<TextView>(R.id.txt_description)
        val txtNameCard         = layout.findViewById<TextView>(R.id.txt_name_card)
        val txtVencDard         = layout.findViewById<TextView>(R.id.txt_venc_card)
        val simbleFlag          = layout.findViewById<ImageView>(R.id.simble_flag)
        val addExpenseCard      = layout.findViewById<TextView>(R.id.txt_add_expense_card)

        buildCard(              progresBarCard,
                                txtDescription,
                                txtNameCard,
                                txtVencDard,
                                simbleFlag,
                                card,
                                addExpenseCard
                 )

        return layout
    }


    private fun buildCard(  progresBarCard   : ProgressBar,
                            txtDescription   : TextView,
                            txtNameCard      : TextView,
                            txtVencDard      : TextView,
                            simbleFlag       : ImageView,
                            card             : Card,
                            addExpenseCard   : TextView
    ){

        addExpenseCard.setOnClickListener {
            openAddExpense( card )
        }

        txtDescription.text = buildDescription( card.balance, card.limit )
        txtNameCard   .text = card.name
        txtVencDard   .text = buildVencDescription( card.dueDate )

        buildProgressBar( progresBarCard, card )
        changeImage     ( simbleFlag,     card )
    }

    private fun openAddExpense( card: Card ) {
        val intent = Intent(context, AddExpenseActivity ::class.java)
        intent.putExtra( Helper.UUIDCARD, card )
        context.startActivity( intent )
    }

    private fun changeImage( simbleFlag: ImageView, card: Card ) {

        if ( card.flagCards.name == FlagCards.MASTERCARD.name ){
            simbleFlag.setImageResource( R.drawable.ic_simble_mastercard )
        }

        else if ( card.flagCards.name == FlagCards.VISA.name ){
            simbleFlag.setImageResource( R.drawable.ic_simble_visa )
        }

    }

    private fun buildProgressBar( progresBarCard: ProgressBar, card: Card ){
        var progress = .0

        for ( expense in card.expenses ) {
            progress += expense.value
        }

        progresBarCard.max      = card.limit.toInt()
        progresBarCard.progress = progress.toInt()
    }

    private fun getDueData( dueDay : Int ) : String {
        val dateFormat  = SimpleDateFormat("dd/MM/yyyy")
        val date        = Date()
        val now = dateFormat.format(date).toString()

        val nowArray = now.split("/")

        var month   = Integer.parseInt( nowArray[1] )
        val day     = Integer.parseInt( nowArray[0] )

        if ( day >= dueDay ) {
            month ++;
        }

        return "$dueDay/$month"
    }

    private fun buildVencDescription( dueDate : Int )                       = "Venc: ${getDueData( dueDate )}"

    private fun buildDescription    ( balance : Double, limit : Double )    = "R$$balance de R$$limit"

    override fun getItem(position: Int): Any {
        return listCard[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listCard.size
    }


}