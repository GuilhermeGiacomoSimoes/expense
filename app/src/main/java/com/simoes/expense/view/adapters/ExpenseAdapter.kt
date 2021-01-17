package com.simoes.expense.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.simoes.expense.R
import com.simoes.expense.helpers.Helper
import com.simoes.expense.helpers.TypeExpense
import com.simoes.expense.model.models.Expense
import java.util.*

class ExpenseAdapter(private var listExpense: ArrayList<Expense>, private var context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val expense = listExpense[position]
        val layout : View

        layout = if ( convertView == null ) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.expenses_adapter, null)
        } else {
            convertView
        }

        val txtNameExpense      = layout.findViewById<TextView>(R.id.txt_name_expense)
        txtNameExpense.text     = expense.name

        val txtDueDate          = layout.findViewById<TextView>(R.id.txt_due_date)
        txtDueDate.text         = expense.dueDate

        val txtValueExpense     = layout.findViewById<TextView>(R.id.txt_value_expense)
        txtValueExpense.text    = expense.value.toString()

        val imgExpense          = layout.findViewById<ImageView>(R.id.img_expense)

        val imageCardOrMoney    = getImageCardOrMoney( expense )
        imgExpense.setImageResource( imageCardOrMoney )

        if ( Helper.expenseOwn( expense ) ) {
            if ( ! expense.paidOut ){
                txtNameExpense.setTextColor(Color.parseColor("#FF0000"))
            }
            else {
                txtNameExpense.setTextColor(Color.parseColor("#008000"))
            }
        }
        else if(expense.paidOut) {
            txtNameExpense.setTextColor(Color.parseColor("#008000"))
        }

        return layout
    }

    override fun getItem(position: Int): Any {
        return listExpense[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listExpense.size
    }

    private fun getImageCardOrMoney( expense : Expense ) : Int {
        return if ( expense.typeExpense == TypeExpense.MONEY ){
            R.drawable.ic_money_expense
        } else {
            R.drawable.ic_card_expense
        }
    }

    fun remove (){
        listExpense.removeAll(listExpense)
        notifyDataSetChanged()
    }
}