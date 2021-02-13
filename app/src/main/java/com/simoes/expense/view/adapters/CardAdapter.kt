package com.simoes.expense.view.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.simoes.expense.R
import com.simoes.expense.controller.CRUDController
import com.simoes.expense.helpers.CallBackReturn
import com.simoes.expense.helpers.FlagCards
import com.simoes.expense.helpers.Helper
import com.simoes.expense.model.CRUDModel
import com.simoes.expense.model.models.Card
import com.simoes.expense.model.models.Expense
import com.simoes.expense.view.activitys.AddExpenseActivity
import com.simoes.expense.view.activitys.ListCardActivity
import com.simoes.expense.view.fragments.FeedbackDialog
import java.text.SimpleDateFormat
import java.util.*

class CardAdapter(private var listCard: ArrayList<Card>, private var context: Context ) : BaseAdapter(), CallBackReturn {

    lateinit var loadingChangeCard   : ProgressBar
    lateinit var cardView            : ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val card = listCard[position]

        val layout : View = if ( convertView == null ) {
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
        val txtDeleteCard       = layout.findViewById<TextView>(R.id.txt_delete_card)
        val txtPayInvoice       = layout.findViewById<TextView>(R.id.txt_pay_invoice)
        val loadingChangeCard   = layout.findViewById<ProgressBar>(R.id.loading_change_card)
        val cardView            = layout.findViewById<ConstraintLayout>(R.id.cardView)
        val txtAmount           = layout.findViewById<TextView>(R.id.txt_amount)

        this.loadingChangeCard = loadingChangeCard
        this.cardView           = cardView

        configButtons( card, txtDeleteCard, txtPayInvoice, loadingChangeCard, cardView)

        buildCard(              progresBarCard,
                                txtDescription,
                                txtNameCard,
                                txtVencDard,
                                simbleFlag,
                                card,
                                addExpenseCard,
                                position,
                                txtAmount
                 )

        return layout
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configButtons(card : Card, txtDeleteCard : TextView, txtPayInvoice : TextView, loadingChangeCard : ProgressBar, cardView : ConstraintLayout) {
        txtDeleteCard.setOnClickListener {
            val builder = AlertDialog.Builder( context )
            builder.setTitle("Confirmaçao")
            builder.setMessage("Tem certeza que deseja excluir o cartão?")
            builder.setPositiveButton("Excluir") { _, _ ->

                loadingChangeCard.visibility    = View.VISIBLE
                cardView.visibility             = View.GONE

                for ( expense in card.expenses ){
                    CRUDModel.delete(expense, expense.uuid, this)
                }

                CRUDModel.delete(card, card.uuid, this)

                (context as ListCardActivity).reload()
            }
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        txtPayInvoice.setOnClickListener {

            if ( expenseAmountIsGreaterThanTheBalance(card) ) {
                FeedbackDialog.showDialog( ( context as ListCardActivity ).supportFragmentManager, "Você não tem saldo o suficiente para efeturar o pagamento", "Saldo insuficiente" )
            } else {
                loadingChangeCard.visibility    = View.VISIBLE
                cardView.visibility             = View.GONE
                payInvoice( card )
            }
        }
    }

    private fun expenseAmountIsGreaterThanTheBalance( card: Card ) : Boolean {
        var valueExpenses = .0

        for ( expense in card.expenses ) {
            valueExpenses += expense.value
        }

        return valueExpenses > card.balance
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun payInvoice(card: Card ) {
        var valuePaid = 0.0

        for ( expense in card.expenses ) {
            if ( ( ! expense.paidOut) && checkExpiration(expense, card) ) {
                valuePaid += expense.value
                expense.paidOut = true

                val cardToExpense: Card = card
                cardToExpense.expenses = ArrayList<Expense>()
                expense.card = cardToExpense
                CRUDController.update( expense, (context as ListCardActivity).supportFragmentManager, context, this )
            }
        }

        card.balance -= valuePaid
        CRUDController.update( card, (context as ListCardActivity).supportFragmentManager, context, this )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildCard(progresBarCard    : ProgressBar,
                          txtDescription    : TextView,
                          txtNameCard       : TextView,
                          txtVencDard       : TextView,
                          simbleFlag        : ImageView,
                          card              : Card,
                          addExpenseCard    : TextView,
                          position          : Int,
                          txtAmount         : TextView
    ){

        addExpenseCard.setOnClickListener {
            openAddExpense( card )
        }

        val totalExpenses = getTotalExpenses( card, position )

        txtDescription.text = buildDescription( totalExpenses, card.limit )
        txtNameCard   .text = card.name
        txtVencDard   .text = buildVencDescription( card.dueDate )

        buildProgressBar( progresBarCard, card.limit.toInt(), totalExpenses.toInt() )
        changeImage     ( simbleFlag,     card )
        buildTxtAmount  ( txtAmount, position )
    }

    private fun buildTxtAmount( txtAmount : TextView, position: Int ) {
        txtAmount.text = "Saldo: ${listCard[position].balance}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTotalExpenses(card: Card, position: Int) : Double {
        var progress = .0

        for ( expense in card.expenses ) {
            if ( ( ! expense.paidOut)  && checkExpiration( expense, card ) ) {
                progress += expense.value
            }
        }

        return progress
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkExpiration(expense: Expense, card: Card ) : Boolean {
        val now = Helper.dateNow()
        val dayNow = Integer.parseInt(now.split(" ")[0].split("/")[2])
        val monthNow = Integer.parseInt(now.split(" ")[0].split("/")[1])
        val yearNow = Integer.parseInt(now.split(" ")[0].split("/")[0])

        var monthInitial   = monthNow
        var yearInitial    = yearNow

        var monthEnd   = monthNow
        var yearEnd    = yearNow

        if ( card.dueDate < dayNow) {
            if (monthEnd < 11) {
                monthEnd ++
            }else {
                monthEnd = 1
                yearEnd ++
            }
        } else {
            if ( monthInitial > 1 ) {
                monthInitial --
            }else {
                monthInitial = 12
                yearInitial --
            }
        }

        val stringInitialDate   = "${card.dueDate}/$monthInitial/$yearInitial"
        val stringEndDate       = "${card.dueDate}/$monthEnd/$yearEnd"

        val initialMilli        = SimpleDateFormat("dd/MM/yyyy").parse(stringInitialDate).time
        val endMilli            = SimpleDateFormat("dd/MM/yyyy").parse(stringEndDate).time
        val expenseDueDateMilli = SimpleDateFormat("dd/MM/yyyy").parse(expense.dueDate).time

        return expenseDueDateMilli in initialMilli..endMilli
    }

    private fun openAddExpense( card: Card ) {
        val intent = Intent(context, AddExpenseActivity ::class.java)
        intent.putExtra( Helper.UUIDCARD, card )
        context.startActivity( intent )
    }

    private fun changeImage( simbleFlag: ImageView, card: Card ) {
        when (card.flagCards.name) {
            FlagCards.MASTERCARD.name -> {
                simbleFlag.setImageResource( R.drawable.ic_simble_mastercard )
            }
            FlagCards.VISA.name -> {
                simbleFlag.setImageResource( R.drawable.ic_simble_visa )
            }
            FlagCards.ELO.name -> {
                simbleFlag.setImageResource( R.drawable.ic_elo )
            }
        }
    }

    private fun buildProgressBar( progresBarCard: ProgressBar, limit : Int, progress : Int ){
        progresBarCard.max      = limit
        progresBarCard.progress = progress
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

    private fun buildVencDescription( dueDate : Int )                               = "Venc: ${getDueData( dueDate )}"

    private fun buildDescription    ( totalExpenses : Double, limit : Double )      = "R$$totalExpenses de R$$limit"

    override fun getItem(position: Int): Any {
        return listCard[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listCard.size
    }

    override fun callback(list: ArrayList<Any>) {

    }

    override fun callback(isSuccess: Boolean) {
        if ( isSuccess ) {
            this.loadingChangeCard.visibility    = View.GONE
            this.cardView.visibility             = View.VISIBLE

            ( context as ListCardActivity ).onActivityResult( Helper.EXPENSE_PAY_INVOICE, Activity.RESULT_OK, Intent() )
        }
    }
}